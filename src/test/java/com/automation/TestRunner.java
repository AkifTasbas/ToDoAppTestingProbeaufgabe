package com.automation;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class TestRunner {

    AppiumDriver<MobileElement> driver;

//---------------------------------------TEST1 Hinzufügen einer Aufgabe ------------------------------------------------------------
    //Plus Button klicken      //Add here control of "Lets add mew Task!" Dialog is oppened or not!!!!!!!!

        /*  TEST1 Hinzufügen einer Aufgabe
      Schritt   Aktion                          Erwartetes Ergebnis
      1         Plus Button klicken            "Lets add mew Task!" Dialog öffnet sich
      2         "Test" in Textfeld schreiben
      3         done klicken                    Neuer Task "Test" wurde erstellt und ist in der Liste sichtbar

         */

    @Test
    public void test1() throws InterruptedException {

        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        //we use android phone
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
        //version of android
        desiredCapabilities.setCapability(MobileCapabilityType.VERSION, "8.0");
        //name of the device, if it is real device , need to pass UUID parameter
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "ToDoApp");
        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 20000);
        //either you specify app--> //path/to/app.apk
        //or if app is already installed, you need to specify appActivity and appPackage
        //this info you can find in the internet or with help of Apk Info app, at work - should ask to developers

        // Set your application's package name.
        desiredCapabilities.setCapability("appPackage", "com.example.yeshasprabhakar.todo");

        // Set your application's MainActivity i.e. the LAUNCHER activity name.
        desiredCapabilities.setCapability("appActivity", "com.example.yeshasprabhakar.todo.MainActivity");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");


        /*"http://localhost:4723/wd/hub" --> address of the appium server. If I have appium server on the same computer
        just use local host
        4723-->default port number
        //we need to provide 2 parameters: URL of appium servers and desired capabilities

        */
        System.out.println("---------------------------The problem is Here!!!!!!!!-----------------");
        System.out.println("----------------------Control 1-------------------------------");
        try {
            driver = new AppiumDriver<>(new URL("http://localhost:49151/wd/hub"), desiredCapabilities);
        } catch (Exception e) {
            System.out.println("------------------Control 2-----------------------------");
            e.printStackTrace();
        }
        System.out.println("-----------------------Control 3-----------------------------------");
        Thread.sleep(3000);



        MobileElement plusButton = driver.findElement(By.id("com.example.yeshasprabhakar.todo:id/fab"));
        plusButton.click();
        //"Test" in Textfeld schreiben
        MobileElement textfeld = driver.findElement(By.xpath("//*[@text='My Task']"));
        textfeld.sendKeys("Test");
        //done klicken
        MobileElement doneButton = driver.findElement(By.id("android:id/button1"));
        doneButton.click();
        //Neuer Task "Test" wurde erstellt und ist in der Liste sichtbar

        //I should make this method dynamic!!!!!!!!!!!!!!
        MobileElement taskTitle = driver.findElement(By.xpath("//*[@text='Test']"));
        Assert.assertTrue(taskTitle.isDisplayed());

        //close the app at the end
        driver.closeApp();
    }
//-------------------------------------------------TEST 2 Hinzufügen einer Aufgabe abbrechen----------------------------

    @Test
    public void test2() throws MalformedURLException, InterruptedException {
        try {
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
            desiredCapabilities.setCapability(MobileCapabilityType.VERSION, "8.0");
            desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "ToDoApp");
            desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 20000);
            desiredCapabilities.setCapability("appPackage", "com.example.yeshasprabhakar.todo");
            desiredCapabilities.setCapability("appActivity", "com.example.yeshasprabhakar.todo.MainActivity");
            desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
            Thread.sleep(3000);

            driver = new AppiumDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


      /*  2. Hinzufügen einer Aufgabe abbrechen
        Schritt   Aktion                          Erwartetes Ergebnis
        1         Plus Button klicken            "Lets add mew Task!" Dialog öffnet sich
        2         "Test" in Textfeld schreiben
        3         cancel klicken                 Es wurde kein neuer Task hinzugefügt, oben steht noch immer "What do you want to do today?"

        */

        //Plus Button klicken
        MobileElement plusButton = driver.findElement(By.id("com.example.yeshasprabhakar.todo:id/fab"));
        plusButton.click();

        //"Test" in Textfeld schreiben
        MobileElement textfeld = driver.findElement(By.xpath("//*[@text='My Task']"));
        textfeld.sendKeys("Test");

        // cancel klicken
        MobileElement cancelButton = driver.findElement(By.id("android:id/button2"));
        cancelButton.click();

        // Es wurde kein neuer Task hinzugefügt, oben steht noch immer "What do you want to do today?"
        MobileElement firstPageText = driver.findElement(By.xpath("//*[@text='What do you want to do today?\n" +
                "You can create new ToDo \n" +
                "using + button\n" +
                "\n" +
                "']"));
        String textOfFirstPage = firstPageText.getText();
        System.out.println("textOfFirstPage = " + textOfFirstPage);
        Assert.assertTrue(textOfFirstPage.contains("What do you want to do today?"));

        //close the app at the end
        driver.closeApp();

    }

//-------------------------------------------------Test3 Hinzufügen einer Aufgabe ohne Titel----------------------------


     /*
     3. Hinzufügen einer Aufgabe ohne Titel
        Schritt   Aktion                          Erwartetes Ergebnis
        1         Plus Button klicken            "Lets add mew Task!" Dialog öffnet sich
        2         done klicken                   Fehlermeldung "Oops, Cannot set an empty text" erscheint

        */

    @Test
    public void test3() throws MalformedURLException, InterruptedException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
        desiredCapabilities.setCapability(MobileCapabilityType.VERSION, "8.0");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "ToDoApp");
        desiredCapabilities.setCapability("appPackage", "com.example.yeshasprabhakar.todo");
        desiredCapabilities.setCapability("appActivity", "com.example.yeshasprabhakar.todo.MainActivity");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");

        driver = new AppiumDriver<>(new URL("http://localhost:4723/wd/hub"), desiredCapabilities);

        Thread.sleep(3000);

        //Plus Button klicken
        MobileElement plusButton = driver.findElement(By.id("com.example.yeshasprabhakar.todo:id/fab"));
        plusButton.click();
        Thread.sleep(3000);
        //done klicken
        MobileElement doneButton = driver.findElement(By.id("android:id/button1"));
        doneButton.click();
        //  Fehlermeldung "Oops, Cannot set an empty text" erscheint
        //I should find out how i can handle with toastMsg(toast messages) !!!!!!!!!!!!!!!!!!!!!

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String textOfAlert = alert.getText();
        System.out.println("textOfAlert = " + textOfAlert);

        //close the app at the end
        driver.closeApp();
    }
    //---------------------------------------------TEST4 Hinzugefügte Aufgabe löschen-----------------------------------

    /*
    TEST4 Hinzugefügte Aufgabe löschen
      Schritt   Aktion                          Erwartetes Ergebnis
      1         Plus Button klicken            "Lets add mew Task!" Dialog öffnet sich
      2         "Test" in Textfeld schreiben
      3         done klicken                    Neuer Task "Test" wurde erstellt und ist in der Liste sichtbar
      4         Mülltonnen Icon klicken         "Deleted successfully" erscheint Aufgabe wurde aus der Liste entfernt

      */
    @Test
    public void test4() throws MalformedURLException, InterruptedException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
        desiredCapabilities.setCapability(MobileCapabilityType.VERSION, "8.0");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "ToDoApp");
        desiredCapabilities.setCapability("appPackage", "com.example.yeshasprabhakar.todo");
        desiredCapabilities.setCapability("appActivity", "com.example.yeshasprabhakar.todo.MainActivity");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");

        driver = new AppiumDriver<>(new URL("http://localhost:4723/wd/hub"), desiredCapabilities);

        Thread.sleep(3000);
        MobileElement plusButton = driver.findElement(By.id("com.example.yeshasprabhakar.todo:id/fab"));
        plusButton.click();
        //"Test" in Textfeld schreiben
        MobileElement textfeld = driver.findElement(By.xpath("//*[@text='My Task']"));
        textfeld.sendKeys("Test");
        //done klicken
        MobileElement doneButton = driver.findElement(By.id("android:id/button1"));
        doneButton.click();
        //Neuer Task "Test" wurde erstellt und ist in der Liste sichtbar
        //I should make this method dynamic!!!!!!!!!!!!!!
        MobileElement taskTitle = driver.findElement(By.xpath("//*[@text='Test']"));
        Assert.assertTrue(taskTitle.isDisplayed());

        // Mülltonnen Icon klicken         "Deleted successfully" erscheint Aufgabe wurde aus der Liste entfernt

        MobileElement mülltonnenIcon = driver.findElement(By.id("com.example.yeshasprabhakar.todo:id/delete"));
        mülltonnenIcon.click();

        //close the app at the end
        driver.closeApp();
    }
//----------------------------------------------TEST5 Tag-/Nachtmodus wechseln------------------------------------------

    /*
    TEST5  Tag-/Nachtmodus wechseln
       Schritt   Aktion                          Erwartetes Ergebnis
       1         Sonne/Mond Icon klicken         Hintergrund ist dunken
       2         Sonne/Mond Icon klicken         Hintergrund ist hell

       */
    @Test
    public void test5() throws MalformedURLException, InterruptedException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
        desiredCapabilities.setCapability(MobileCapabilityType.VERSION, "8.0");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "ToDoApp");
        desiredCapabilities.setCapability("appPackage", "com.example.yeshasprabhakar.todo");
        desiredCapabilities.setCapability("appActivity", "com.example.yeshasprabhakar.todo.MainActivity");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");

        driver = new AppiumDriver<>(new URL("http://localhost:4723/wd/hub"), desiredCapabilities);

        Thread.sleep(3000);
        // 1         Sonne/Mond Icon klicken         Hintergrund ist dunken
        //I should check the background chanced or not!!!!!!!!!!!!!!!!!!!
        MobileElement sonneMondIcon = driver.findElement(By.id("com.example.yeshasprabhakar.todo:id/themeActionButton"));
        sonneMondIcon.click();

        Thread.sleep(5000);
        //I should check the background chanced or not!!!!!!!!!!!!!!!!!!!
        //2         Sonne/Mond Icon klicken         Hintergrund ist hell
        MobileElement sonneIcon = driver.findElement(By.id("com.example.yeshasprabhakar.todo:id/themeActionButton"));
        sonneIcon.click();
        //close the app at the end
        driver.closeApp();
    }


}

