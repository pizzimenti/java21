import java.util.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.lang.Thread;

import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      return new ModelAndView (model, layout);
    }, new VelocityTemplateEngine());

    get("/replay", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      List<Turn> turns = Turn.all();
      String color = newTurn.getGeneratedColor();
      if(color.equals("red")) {
        newTurn.updateShownStatus();
        response.redirect("/red");
        return null;
      }
      response.redirect("/play");
      return null;
    });

    get("/red", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/red.vtl");
      return new ModelAndView (model, layout);
    }, new VelocityTemplateEngine());


    get("/index4", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/index4.vtl");
      return new ModelAndView (model, layout);
    }, new VelocityTemplateEngine());

  } //end of main

  // public static void timer() {
  //
  //   long startTime = System.currentTimeMillis(); //fetch starting time
  //   while(false||(System.currentTimeMillis()-startTime)<5000)
  //   {
  //       get("/")
  //   }
  // }

}
