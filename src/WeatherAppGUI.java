import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherAppGUI extends JFrame {

    private JSONObject weatherData;
    public WeatherAppGUI(){
        super("Weather App");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 650);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        addGuiCompontens();
    }

    private void addGuiCompontens(){
        // Search field
        JTextField searchTextField = new JTextField();

        searchTextField.setBounds(15, 15, 351, 45);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextField);

        // Temperature label
        JLabel temperatureText = new JLabel("10°C");
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);
        add(temperatureText);

        // Weather image
        JLabel weatherConditionImg = new JLabel(loadImage("src/icons/cloudy.png"));
        weatherConditionImg.setBounds(50, 125, 350, 217);
        add(weatherConditionImg);

        // Weather description
        JLabel weatherConditionDesc = new JLabel("Cloudy");
        weatherConditionDesc.setBounds(0, 405, 450, 36);
        weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDesc);

        // Humidity image
        JLabel humidityImg = new JLabel(loadImage("src/icons/humidity.png"));
        humidityImg.setBounds(15, 500, 74, 66);
        add(humidityImg);

        // Humidity text
        JLabel humidityTxt = new JLabel("<html><b>Humidity</b> 100%</html>");
        humidityTxt.setBounds(90, 500, 85, 55);
        humidityTxt.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityTxt);

        // Windspeed image
        JLabel windspeedImg = new JLabel(loadImage("src/icons/windspeed.png"));
        windspeedImg.setBounds(250, 500, 74, 66);
        add(windspeedImg);

        // Windspeed text
        JLabel windspeedTxt = new JLabel("<html><b>Windspeed</b> 15 km/h</html>");
        windspeedTxt.setBounds(340, 500, 90, 55);
        windspeedTxt.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedTxt);

        // Search button
        JButton searchBtn = new JButton(loadImage("src/icons/search.png"));
        searchBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        searchBtn.setBounds(375, 13, 50, 50);
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get location from user
                String userInput = searchTextField.getText();

                // validate input - remove whitespace to ensure non-empty text
                if(userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }

                // retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                // update gui

                // update weather image
                String weatherCondition = (String) weatherData.get("weather_condition");

                // depending on the condition we will update the weather image that corresponds with the condition
                switch (weatherCondition){
                    case "Clear":
                        weatherConditionImg.setIcon(loadImage("src/icons/clear.png"));

                        break;
                    case "Rain":
                        weatherConditionImg.setIcon(loadImage("src/icons/rain.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImg.setIcon(loadImage("src/icons/cloudy.png"));
                        break;
                    case "Snow":
                        weatherConditionImg.setIcon(loadImage("src/icons/snow.png"));
                        break;
                }

                // update temperature text
                double temperature = (double) weatherData.get("temperature");
                temperatureText.setText(temperature + " °C");

                // update weather condition text
                weatherConditionDesc.setText(weatherCondition);

                // update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityTxt.setText("<html><b>Humidity>/b> " + humidity + "%</html>");

                // update windspeed text
                double windspeed = (double) weatherData.get("windspeed");
                humidityTxt.setText("<html><b>Windspeed>/b> " + windspeed + " km/h</html>");

            }
        });
        add(searchBtn);
    }

    private ImageIcon loadImage(String ressourcePath){
        try{
            BufferedImage img = ImageIO.read(new File(ressourcePath));
            return new ImageIcon(img);
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("couldn't find ressources");
        return null;
    }
}
