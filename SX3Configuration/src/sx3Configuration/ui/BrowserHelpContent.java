package sx3Configuration.ui;

import java.io.File;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class BrowserHelpContent extends Region{
	

    final WebView browser = new WebView();
    final WebEngine webEngine = browser.getEngine();
     
    public BrowserHelpContent() {
        //apply the styles
        getStyleClass().add("browser");
        
        File f = new File("C:\\Users\\Lenovo\\git\\sx3-configuration-tool\\SX3Configuration\\src\\sx3Configuration\\webView\\sample.html");
        webEngine.load(f.toURI().toString());
//        webEngine.load("http://www.oracle.com/products/index.html");
        //add the web view to the scene
        getChildren().add(browser);
 
    }
    @Override protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browser,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }
 
    @Override protected double computePrefWidth(double height) {
        return 750;
    }
 
    @Override protected double computePrefHeight(double width) {
        return 500;
    }

}
