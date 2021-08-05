package imageTrend;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.awt.GridLayout;
import java.awt.Label;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.ImageIcon;

public class GUI extends JFrame implements ActionListener{

	private final JFileChooser fileDialog = new JFileChooser("C:\\Users\\ADMIN\\Pictures");
	private JFrame mainFrame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setResizable(false);
		mainFrame.setPreferredSize(new Dimension(2000, 2000));
		mainFrame.setTitle("Xu ly anh");
		mainFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\icon app.jpg"));
		mainFrame.setBounds(100, 100, 780, 368);
//		mainFrame.setDefaultCloseOperation(mainFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new GridLayout(0, 2, 5, 5));
		
		JButton btnThemFile = new JButton("");
		btnThemFile.setBackground(new Color(255, 255, 255));
		btnThemFile.setIcon(new ImageIcon("src\\add file.png"));
		btnThemFile.addActionListener(this);
		
		JButton button_1 = new JButton("");
		button_1.setVerticalAlignment(SwingConstants.TOP);
		button_1.setIcon(new ImageIcon("src\\meme.jpg"));
		mainFrame.getContentPane().add(button_1);
		mainFrame.getContentPane().add(btnThemFile);
		
		JButton button_2 = new JButton("");
		button_2.setIcon(new ImageIcon("src\\meme.jpg"));
		mainFrame.getContentPane().add(button_2);
		
		JButton button = new JButton("");
		button.setBackground(new Color(255, 255, 255));
		button.setIcon(new ImageIcon("src\\thanks.png"));
		mainFrame.getContentPane().add(button);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setVerticalAlignment(SwingConstants.BOTTOM);
		btnNewButton.setIcon(new ImageIcon("src\\meme.jpg"));
		mainFrame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setBackground(new Color(255, 255, 255));
		btnNewButton_1.setIcon(new ImageIcon("src\\author.png"));
		mainFrame.getContentPane().add(btnNewButton_1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		int returnVal = fileDialog.showOpenDialog(mainFrame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileDialog.getSelectedFile();
            BufferedImage img = null;
            File f = null;

            // đọc hình ảnh từ máy tính
            String filePath = file.getAbsolutePath();
            try {
                f = new File(filePath);
                img = ImageIO.read(f);
            } catch (IOException e1) {
                System.out.println(e1);
            }

            // lấy chiều cao và chiều rộng của ảnh
            int width = img.getWidth();
            int height = img.getHeight();
            
            BufferedReader bufferedReader = null;
            BufferedWriter bufferedWriter = null;
            
            StringBuilder stringBuilder = new StringBuilder();
            
            for (int y = 0; y < height; y += 3) {
               for (int x = 0; x < (width); x += 2) {
                   // x,y là toạ độ của ảnh để sửa các giá trị pixel
                   int p = img.getRGB(x, y);

                   int a = (p >> 24) & 0xff;
                   int r = (p >> 16) & 0xff;
                   int g = (p >> 8) & 0xff;
                   int b = p & 0xff;

                   // tính giá trị trung bình
                   int avg = (r + g + b) / 3;

                   // thay RGB bằng giá trị avg vừa tính được
                   p = (a << 24) | (avg << 16) | (avg << 8) | avg;
                   Color mycolor = new Color(p);
                   r = mycolor.getRed();
                   g = mycolor.getGreen();
                   b = mycolor.getBlue();
                   p = (r + g + b) / 75 + 32; 
                   char c = (char) p;
                   stringBuilder.append(c + "");
               }
               stringBuilder.append("\n");
            }          
            JFrame outFrame = new JFrame();
            outFrame.setTitle("Anh den trang");
            outFrame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\icon app.jpg"));
            outFrame.setBounds(100, 100, 780, 368);
//            outFrame.setDefaultCloseOperation(outFrame.EXIT_ON_CLOSE);
            outFrame.setVisible(true);
            JPanel panel = new JPanel(new BorderLayout());
            JTextArea jtArea = new JTextArea(stringBuilder.toString());
            Font font = new Font("Consolas", Font.CENTER_BASELINE, 2);
            jtArea.setFont(font);
            JScrollPane jScrollPane = new JScrollPane(jtArea);
            panel.add(new JLabel("Preview"), BorderLayout.PAGE_START);
            panel.add(jScrollPane, BorderLayout.CENTER);
            outFrame.getContentPane().add(panel);
    		pack();
        } else {
            // To-Do
        }
	}
}
