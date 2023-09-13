package java_projects;

import java.io.*;
import java.net.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import com.google.gson.Gson;

public final class NASA_APOD {
    public static void main(String[] args) throws Exception {
        Gson gson = new Gson();

        URL url = new URL("https://api.nasa.gov/planetary/apod?api_key=NVatEqEaLx6kA5zja8TRE4KZ0Oed3Ruf1th4XCYQ");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println("Processing response");
            response.append(line);
        }
        reader.close();

        String jsonResponse = response.toString();

        Apod apod = gson.fromJson(jsonResponse, Apod.class);

        String imageURL = apod.getUrl();
        String explanation = apod.getExplantion();
        String title = apod.getTitle();

        // System.out.println("Image URL: " + imageURL);
        // System.out.println();
        // System.out.println();
        System.out.println(jsonResponse);

        URL APOD_URL = new URL(imageURL);
        BufferedImage attemptImage = ImageIO.read(APOD_URL);

        // create image icon from the image url
        Image scaledImage = attemptImage.getScaledInstance(400, 600, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(imageIcon);
        JLabel titleLabel = new JLabel(title);
        JLabel explanationLabel = new JLabel("<html>" + explanation + "<html>");

        // Customizations
        Font titleFont = new Font("Georgia", Font.BOLD, 20);
        explanationLabel.setForeground(Color.WHITE);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(titleFont);

        JFrame frame = new JFrame("NASA APOD");
        frame.getContentPane().setBackground(Color.BLACK);

        //Text area for explanation
        JTextArea explanationArea = new JTextArea(explanation);
        explanationArea.setBackground(Color.BLACK);
        explanationArea.setForeground(Color.WHITE);
        explanationArea.setLineWrap(true);
        explanationArea.setWrapStyleWord(true);
        explanationArea.setEditable(false);



        frame.setLayout(new BorderLayout());
        frame.setSize(800, 900);

        frame.add(titleLabel, BorderLayout.NORTH);
        frame.add(imageLabel, BorderLayout.WEST);
        frame.add(explanationArea, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
