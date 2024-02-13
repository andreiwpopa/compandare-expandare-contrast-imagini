package proiectProcesImagini;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ImageMain extends JFrame {

	private BufferedImage inputImage;
    private BufferedImage processedImage;
    private double intensityLevel = 1.0;
    private double contrastLevel = 1.0;
	
	public ImageMain() {
		
        setTitle("Contrast Modification App");

        // Adăugare câmp pentru adăugarea imaginii
        JTextField imagePathField = new JTextField();
        JButton browseButton = new JButton("Browse");
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a file chooser dialog
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Select Image");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "bmp"));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // Get the selected file
                    File selectedFile = fileChooser.getSelectedFile();
                    // Update the text field with the selected file path
                    imagePathField.setText(selectedFile.getAbsolutePath());
                    // Load the selected image
                    try {
                        inputImage = ImageIO.read(selectedFile);
                        // Verificăm dacă imaginea este pe 8 sau 24 de biți
                        if (isImage8Or24Bit(inputImage)) {
                            // Dacă imaginea are 8 sau 24 de biți, afișăm imaginea originală
                            displayOriginalImage();
                        } else {
                            // Altfel, afișăm un mesaj de eroare
                            JOptionPane.showMessageDialog(null, "Imaginea selectată nu este pe 8 sau 24 de biți.", "Eroare", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // Adăugare meniu derulant pentru selecția tipului de modificare
        String[] modificationTypes = {"Compandare domeniul", "Expandare domeniul"};
        JComboBox<String> modificationComboBox = new JComboBox<>(modificationTypes);

        
        // Adding formatted text fields for intensity and contrast levels
        NumberFormatter formatter = new NumberFormatter(new DecimalFormat("#0.00"));
        formatter.setValueClass(Double.class);
        formatter.setMinimum(0.0);
        JFormattedTextField intensityField = new JFormattedTextField(formatter);
        intensityField.setValue(intensityLevel);
        JFormattedTextField contrastField = new JFormattedTextField(formatter);
        contrastField.setValue(contrastLevel);
        
        // Adding a button to apply the selected modification
        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                intensityLevel = ((Number)intensityField.getValue()).doubleValue();
                contrastLevel = ((Number)contrastField.getValue()).doubleValue();
                
                // Check which modification is selected
                int selectedIndex = modificationComboBox.getSelectedIndex();
                if (selectedIndex == 0) {
                    // Apply companding
                	processedImage = applyCompanding(inputImage, intensityLevel, contrastLevel); // Adjust the companding factor as needed
                } else if (selectedIndex == 1) {
                    // Apply expanding
                	processedImage = applyExpanding(inputImage, intensityLevel, contrastLevel); // Adjust the expanding factor as needed
                }
                // Display the processed image
                displayProcessedImage();
            }
        });
        
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deschidem un dialog de selectare a locației pentru salvarea imaginii
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Image");
                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    // Obținem locația selectată pentru salvarea imaginii
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    // Adăugăm extensia potrivită imaginii (de exemplu, .png)
                    if (!filePath.toLowerCase().endsWith(".png")) {
                        filePath += ".png";
                    }
                    try {
                        // Salvăm imaginea rezultată
                        ImageIO.write(processedImage, "png", new File(filePath));
                        JOptionPane.showMessageDialog(null, "Imaginea a fost salvată cu succes.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "Eroare la salvarea imaginii.", "Eroare", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

     // Layout of GUI
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Create panel for input fields and apply button
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Image Path:"));
        inputPanel.add(imagePathField);
        inputPanel.add(browseButton);
        inputPanel.add(new JLabel("Modification Type:"));
        inputPanel.add(modificationComboBox);
        inputPanel.add(new JLabel("Intensity Level:"));
        inputPanel.add(intensityField);
        inputPanel.add(new JLabel("Contrast Level:"));
        inputPanel.add(contrastField);
        inputPanel.add(applyButton); // Adding apply button to the same panel
        inputPanel.add(saveButton);

        // Create panel to display images
        JPanel imagePanel = new JPanel(new GridBagLayout());

        // Add input panel to the main panel
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(imagePanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setVisible(true);


	}
	
    private void displayOriginalImage() {
        JPanel imagePanel = (JPanel) getContentPane().getComponent(1);
        imagePanel.removeAll();

        if (inputImage != null) {
            ImageIcon originalIcon = new ImageIcon(inputImage);
            JLabel originalLabel = new JLabel(originalIcon);
            originalLabel.setPreferredSize(new Dimension(300, 300));
            imagePanel.add(originalLabel);
        }

        revalidate();
        repaint();
    }

	private void displayProcessedImage() {
        JPanel imagePanel = (JPanel) getContentPane().getComponent(1);
        imagePanel.removeAll();

        if (processedImage != null) {
            ImageIcon processedIcon = new ImageIcon(processedImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH));
            JLabel processedLabel = new JLabel(processedIcon);
            imagePanel.add(processedLabel);
        }

        revalidate();
        repaint();
	}
	
	private boolean isImage8Or24Bit(BufferedImage image) {
	    // Obținem numărul de biți pe pixel al imaginii
	    int bitsPerPixel = image.getColorModel().getPixelSize();
	    
	    // Verificăm dacă imaginea are 8 sau 24 de biți per pixel
	    return bitsPerPixel == 8 || bitsPerPixel == 24;
	}
	
    
	private BufferedImage applyCompanding(BufferedImage inputImage, double intensityLevel, double contrastLevel) {
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();

        BufferedImage rezultat = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		for (int y=0; y < height; y++) {
			for (int x=0; x < width; x++) {
				int pixel = inputImage.getRGB(x, y);
				
				// extrag componentele RGB
				int rosu = (pixel >> 16) & 0xff;
				int verde = (pixel >> 8) & 0xff;
				int albastru = pixel & 0xff;
				
				//aplic compandarea logaritmica pe fiecare componenta
				rosu = (int) (intensityLevel * Math.log(1 + contrastLevel * rosu));
				verde = (int) (intensityLevel * Math.log(1 + contrastLevel * verde));
				albastru = (int) (intensityLevel * Math.log(1 + contrastLevel * albastru));
				
				//asigur ca valorile RGB sunt in intervalul 0-255
				 rosu = Math.min(255, Math.max(0, rosu));
	             verde = Math.min(255, Math.max(0, verde));
	             albastru = Math.min(255, Math.max(0, albastru));
	             
	             //creez noul pixel cu compandarea aplicata
	             int pixelCompandat = (rosu << 16) | (verde << 8) | albastru;
	             //setez pixelul in imaginea rezultat
	             rezultat.setRGB(x, y, pixelCompandat);

			}
		}
		return rezultat;
	}
    
	private BufferedImage applyExpanding(BufferedImage inputImage, double intensityLevel, double contrastLevel) {
		int width = inputImage.getWidth();
		int height = inputImage.getHeight();
		
        BufferedImage rezultat = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = inputImage.getRGB(x, y);
                
                // Extrage componentele RGB
                int rosu = (pixel >> 16) & 0xff;
                int verde = (pixel >> 8) & 0xff;
                int albastru = pixel & 0xff;
                
                // Aplică expandarea domeniului pe fiecare componentă
                rosu = (int) (intensityLevel * (Math.exp(contrastLevel * rosu) - 1));
                verde = (int) (intensityLevel * (Math.exp(contrastLevel * verde) - 1));
                albastru = (int) (intensityLevel * (Math.exp(contrastLevel * albastru) - 1));
                
                // Asigură că valorile RGB sunt în intervalul 0-255
                rosu = Math.min(255, Math.max(0, rosu));
                verde = Math.min(255, Math.max(0, verde));
                albastru = Math.min(255, Math.max(0, albastru));
                
                // Creează noul pixel cu expandarea aplicată
                int pixelExpandat = (rosu << 16) | (verde << 8) | albastru;
                
                // Setează pixelul în imaginea rezultat
                rezultat.setRGB(x, y, pixelExpandat);
            }
        }
        
        return rezultat;
	}
    
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
        SwingUtilities.invokeLater(ImageMain::new);

	}

}
