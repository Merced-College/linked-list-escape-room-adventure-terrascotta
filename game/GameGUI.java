package game;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
public class GameGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private static JTextArea sceneDesc;
	private static JLabel lobbything;
	private static JButton btnChoice1;
	private static JButton btnChoice2;
	private static Scene currentScene;
	private static AdventureGame game;
	private static JTextField userInventory;
	private JTextArea itemsRoom;
	private JButton btnPickUpItem;
	
	/**
	 * Launch the application.
	 */
	
	
	
	public static void main(String[] args) {
		
        //game.play();
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameGUI frame = new GameGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GameGUI() {
		
		game = new AdventureGame();
		currentScene = game.getCurrentScene();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 789, 479);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(128, 128, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel intro = new JLabel("choose the adventure game.");
		intro.setFont(new Font("Impact", Font.PLAIN, 26));
		intro.setBounds(10, 28, 380, 70);
		contentPane.add(intro);
		
		lobbything = new JLabel();
		
		ImageIcon icon = new ImageIcon(GameGUI.class.getResource("/images2/lobby.png"));
		Image img = icon.getImage();

		Image scaledImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImg);

		lobbything.setIcon(scaledIcon);
		lobbything.setBounds(20, 102, 300, 300);
		contentPane.add(lobbything);
		
		
		
		sceneDesc = new JTextArea();
		sceneDesc.setBounds(453, 117, 300, 59);
		contentPane.add(sceneDesc);
		
		btnChoice1 = new JButton("btnChoice1");
		btnChoice1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nextId = currentScene.getChoices().get(0).getNextSceneId();
	            currentScene = game.getScences().findSceneById(nextId);
	            
	            updateSceneDisplay();
	            if (currentScene.getSceneId() == 5) {
	            	checkWinCondition();
	            }
	            
	            
			}
		});
		btnChoice1.setBounds(10, 406, 310, 23);
		contentPane.add(btnChoice1);
		
		btnChoice2 = new JButton("B2");
		btnChoice2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nextId = currentScene.getChoices().get(1).getNextSceneId();
	            currentScene = game.getScences().findSceneById(nextId);
	            updateSceneDisplay();
	            
	            
			}
		});
		btnChoice2.setBounds(453, 406, 310, 23);
		contentPane.add(btnChoice2);
		
		userInventory = new JTextField();
		userInventory.setBounds(473, 187, 260, 86);
		contentPane.add(userInventory);
		userInventory.setColumns(10);
		
		itemsRoom = new JTextArea();
		itemsRoom.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
		itemsRoom.setEnabled(false);
		itemsRoom.setWrapStyleWord(true);
		itemsRoom.setLineWrap(true);
		itemsRoom.setText("Inventory: empty");
		itemsRoom.setColumns(10);
		itemsRoom.setBounds(330, 277, 116, 152);
		
		contentPane.add(itemsRoom);
		
		btnPickUpItem = new JButton("pick up item");
		btnPickUpItem.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        Item itemInRoom = currentScene.getItem();
		 
		        if (itemInRoom != null) {
		            game.getPlayer().addItem(itemInRoom);
		            currentScene.removeItem(); 
		            updateSceneDisplay();
		        }
		    }
		});
		btnPickUpItem.setBounds(342, 243, 89, 23);
		contentPane.add(btnPickUpItem);
		
		updateSceneDisplay();
	}
	
	public void checkWinCondition() {
	    boolean hasKeycard = game.getPlayer().hasItem("Keycard");
	    boolean hasCodeNote = game.getPlayer().hasItem("Code Note");

	    if (hasKeycard && hasCodeNote) {
	        javax.swing.JOptionPane.showMessageDialog(this,
	            "You used the Keycard and the Code Note to unlock the exit.\nYou escaped.");
	    } else {
	        javax.swing.JOptionPane.showMessageDialog(this,
	            "The exit will not open.\nYou are missing the required items.\nYou need to go find that Keycard and Code Note first!");
	    }

	    btnChoice1.setEnabled(false);
	    btnChoice2.setEnabled(false);
	    btnPickUpItem.setEnabled(false);
	}
	
	
	public void updateSceneDisplay() {
	    //Scene scene = game.getCurrentScene();

	    //lblTitle.setText(scene.getTitle());
	    sceneDesc.setText(currentScene.getDescription());
	    System.out.println(currentScene);
	    Item itemInRoom = currentScene.getItem();
	    if(itemInRoom == null)
	    	itemsRoom.setText("sorry nothing");
	    else
	    	itemsRoom.setText(currentScene.getItem().toString());
	    
	    userInventory.setText(game.getPlayer().getInventoryText());

	    ImageIcon icon = new ImageIcon(currentScene.getImagePath());
	    Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
	    lobbything.setIcon(new ImageIcon(img));

	    btnChoice1.setText(currentScene.getChoices().get(0).getText());
	    btnChoice2.setText(currentScene.getChoices().get(1).getText());

	    //btnPickup.setVisible(scene.getItem() != null);
	}
}
