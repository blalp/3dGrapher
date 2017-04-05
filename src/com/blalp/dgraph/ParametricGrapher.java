package com.blalp.dgraph;

import org.jzy3d.maths.Range;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParametricGrapher {
    public static void main(String[] args) {
        try {
            Parametric.main(new String[]{"Math.sin((t))", "Math.cos((t))", "(t)"},new Range(0,500000),1);
        } catch (Exception el){
            el.printStackTrace();
        }
        JFrame window = new JFrame("Inputs");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel pane = new JPanel(new GridBagLayout());

        JButton submit = new JButton("Submit");
        submit.setBackground(java.awt.Color.LIGHT_GRAY);

        JLabel fRulesLbl = new JLabel("<html>Some Rules that you need to fallow for this to work right.<br>"+
        "Acceptable functions: + - * / sin cos tan arccos arctan arcsin<br>"+
        "Special rules for ^: Enclose the two values you want to raise to the power in []. For example t^2 becomes [t]^[2]. Sorry for the inconvenience.<br>"+
        "Have fun! - blalp<br><br>"+
        "Instructions:<br>"+
        "Rotate     : Left click and drag mouse<br>" +
        "Scale      : Roll mouse wheel<br>" +
        "Z Shift    : Right click and drag mouse<br>" +
        "Animate    : Double left click<br>" +
        "Screenshot : Press 's'"
        );

        JLabel xInputLbl = new JLabel("Function to define X.");
        JLabel yInputLbl = new JLabel("Function to define Y.");
        JLabel zInputLbl = new JLabel("Function to define Z.");

        JTextArea xInput = new JTextArea("cos(t)");
        JTextArea yInput = new JTextArea("sin(t)");
        JTextArea zInput = new JTextArea("t");

        JLabel tMaxLbl = new JLabel("Upper bound of t.");
        JLabel tMinLbl = new JLabel("Lower bound of t.");
        JLabel tStepLbl = new JLabel("What t steps by (lower is more accurate and more intensive).");

        JTextArea tMax = new JTextArea("500000");
        JTextArea tMin = new JTextArea("0");
        JTextArea tStep = new JTextArea("1");

        JLabel errMsgLbl = new JLabel("Error box, where you figure out what you did wrong.");

        JTextArea errMsg = new JTextArea("All good :)");

        xInput.setBackground(Color.LIGHT_GRAY);
        yInput.setBackground(Color.LIGHT_GRAY);
        zInput.setBackground(Color.LIGHT_GRAY);

        tMax.setBackground(Color.ORANGE);
        tMin.setBackground(Color.ORANGE);
        tStep.setBackground(Color.ORANGE);

        errMsg.setBackground(java.awt.Color.MAGENTA);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errMsg.setText("");
                if(!validInput(xInput.getText())){
                    errMsg.setText(errMsg.getText()+" x: "+xInput.getText()+" ("+ howInvalidInput(xInput.getText())+")");
                }
                if(!validInput(yInput.getText())){
                    errMsg.setText(errMsg.getText()+" y: "+yInput.getText()+" ("+ howInvalidInput(yInput.getText())+")");
                }
                if(!validInput(zInput.getText())){
                    errMsg.setText(errMsg.getText()+" z: "+zInput.getText()+" ("+ howInvalidInput(zInput.getText())+")");
                }
                if(validInput(xInput.getText())&& validInput(yInput.getText())&& validInput(zInput.getText())) {
                    try {
                        Parametric.main(new String[]{parseString(xInput.getText()),parseString(yInput.getText()),parseString(zInput.getText())},new Range(Float.parseFloat(tMin.getText()),Float.parseFloat(tMax.getText())),Float.parseFloat(tStep.getText()));
                    } catch (Exception el){
                        el.printStackTrace();
                    }
                }
            }
        });
        pane.add(xInputLbl,     configComponent(0,0));
        pane.add(xInput,        configComponent(1,0));
        pane.add(yInputLbl,     configComponent(0,1));
        pane.add(yInput,        configComponent(1,1));
        pane.add(zInputLbl,     configComponent(0,2));
        pane.add(zInput,        configComponent(1,2));
        pane.add(errMsgLbl,     configComponent(0,3));
        pane.add(errMsg,        configComponent(1,3));
        pane.add(tMaxLbl,       configComponent(0,4));
        pane.add(tMax,          configComponent(1,4));
        pane.add(tMinLbl,       configComponent(0,5));
        pane.add(tMin,          configComponent(1,5));
        pane.add(tStepLbl,      configComponent(0,6));
        pane.add(tStep,         configComponent(1,6));
        pane.add(fRulesLbl,     configComponent(0,7,2,1));
        pane.add(submit,        configComponent(0,8,2,1));

        window.add(pane);
        window.pack();
        window.setVisible(true);
        /*switch (mode) {
            case "Surface":
            // Define a function to plot
            Mapper mapper = new Mapper() {
                public double f(double x, double y) {
                    return Math.sin(x / 10) * Math.cos(y / 20);
                }
            };

// Define range and precision for the function to plot
            Range range = new Range(-150, 150);
            int steps = 50;

// Create a surface drawing that function
            Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
            surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new org.jzy3d.colors.Color(1, 1, 1, .5f)));
            surface.setFaceDisplayed(true);
            surface.setWireframeDisplayed(false);
            surface.setWireframeColor(org.jzy3d.colors.Color.RED);
            ParametricGrapher.surface=surface;
            break;
            case "Parametric":*/
                /*try {
                    Parametric.main();
                } catch (Exception e){
                    e.printStackTrace();
                }*/
                //break;
        //}
    }
    private static boolean validInput(String str){
        if(str.contains("T")){
            return false;
        }
        return true;
    }
    private static String howInvalidInput(String input){
        return "is not valid.";
    }
    private static String parseString(String str){
        String output=str;
        output=output.replaceAll("acos","Math.acos");
        output=output.replaceAll("arccos","Math.acos");
        output=output.replaceAll("asin","Math.asin");
        output=output.replaceAll("arcsin","Math.asin");
        output=output.replaceAll("atan","Math.atan");
        output=output.replaceAll("arctan","Math.atan");
        output=output.replaceAll("(?<![ac])sin","Math.sin");
        output=output.replaceAll("(?<![ac])cos","Math.cos");
        output=output.replaceAll("(?<![ac])tan","Math.tan");
        output=output.replaceAll("(?<=\\w)E(?<!\\w)","(Math.E)");
        output=output.replaceAll("(?<=t)E(?=t)","*(Math.E)*");
        output=output.replaceAll("(?<=t)E","*(Math.E)");
        output=output.replaceAll("E(?=t)","(Math.E)*");
        output=output.replaceAll("(?<=\\w)PI(?<!\\w)","(Math.PI)");
        output=output.replaceAll("(?<=\\w)PI(?=\\w)","*(Math.PI)*");
        output=output.replaceAll("(?<=\\w)PI","*(Math.PI)");
        output=output.replaceAll("PI(?=\\w)","(Math.PI)*");
        output=output.replaceAll("logbase\\((.*),(.*)\\)","Math.log($1)/Math.log($2)");
        output=output.replaceAll("(?<!\\.)log","Math.log10");
        output=output.replaceAll("ln\\((.*)\\)","Math.log($1)/Math.log(Math.E)");
        output=output.replaceAll("(\\[.*?)\\^(.*?])","Math.pow($1,$2)").replace("]","").replace("[","");
        //TODO: add peacewise
        //output.replaceAll("\{(.*)|(.*)\}","\{$1||$2\}");
        //output.replaceAll("\{(.*)&(.*)\}","\{$1&&$2\}");
        //output.replaceAll("\((.*)\)\{(.*)\}
        //sync to github
        return output;

    }
    private static GridBagConstraints configComponent(int x,int y) {
        return configComponent(x,y,0.5,0.5,0,0,1,1);
    }
    private static GridBagConstraints configComponent(int x,int y,int gridwidth,int gridheight) {
        return configComponent(x,y,0.5,0.5,0,0,gridwidth,gridheight);
    }
    private static GridBagConstraints configComponent(int x,int y,double wieghtx,double wieghty,int ipadx,int ipady,int gridwidth,int gridheight){
        GridBagConstraints output = new GridBagConstraints();
        output.fill=GridBagConstraints.HORIZONTAL;
        output.fill=GridBagConstraints.HORIZONTAL;
        output.gridx=x;
        output.gridy=y;
        output.weightx=wieghtx;
        output.weighty=wieghty;
        output.ipadx=ipadx;
        output.ipady=ipady;
        output.gridwidth=gridwidth;
        output.gridheight=gridheight;
        return output;
        /*
        JButton button;
pane.setLayout(new GridBagLayout());
GridBagConstraints c = new GridBagConstraints();
if (shouldFill) {
                //natural height, maximum width
                c.fill = GridBagConstraints.HORIZONTAL;
}

button = new JButton("Button 1");
if (shouldWeightX) {
                   c.weightx = 0.5;
}
c.fill = GridBagConstraints.HORIZONTAL;
c.gridx = 0;
c.gridy = 0;
pane.add(button, c);

button = new JButton("Button 2");
c.fill = GridBagConstraints.HORIZONTAL;
c.weightx = 0.5;
c.gridx = 1;
c.gridy = 0;
pane.add(button, c);

button = new JButton("Button 3");
c.fill = GridBagConstraints.HORIZONTAL;
c.weightx = 0.5;
c.gridx = 2;
c.gridy = 0;
pane.add(button, c);

button = new JButton("Long-Named Button 4");
c.fill = GridBagConstraints.HORIZONTAL;
c.ipady = 40;      //make this component tall
c.weightx = 0.0;
c.gridwidth = 3;
c.gridx = 0;
c.gridy = 1;
pane.add(button, c);

button = new JButton("5");
c.fill = GridBagConstraints.HORIZONTAL;
c.ipady = 0;       //reset to default
c.weighty = 1.0;   //request any extra vertical space
c.anchor = GridBagConstraints.PAGE_END; //bottom of space
c.insets = new Insets(10,0,0,0);  //top padding
c.gridx = 1;       //aligned with button 2
c.gridwidth = 2;   //2 columns wide
c.gridy = 2;       //third row
pane.add(button, c);

http://docs.oracle.com/javase/tutorial/figures/uiswing/layout/GridBagLayoutDemo.png
         */
    }
}
