package my_weka.controller;

import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import my_weka.service.WekaDemoService;

@Controller
public class WekaDemoController {

    @Autowired
    private WekaDemoService service;

    @RequestMapping("/")
    @ResponseBody
    public String home() {

        try {
            return test();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ERROR";
    }

    /**
     * runs the program, the command line looks like this:<br/>
     * WekaDemo CLASSIFIER classname [options] FILTER classname [options] DATASET
     * filename <br/>
     * e.g., <br/>
     * java -classpath ".:weka.jar" WekaDemo \<br/>
     * CLASSIFIER weka.classifiers.trees.J48 -U \<br/>
     * FILTER weka.filters.unsupervised.instance.Randomize \<br/>
     * DATASET iris.arff<br/>
     */
    private String test() throws Exception {

        String input = "CLASSIFIER weka.classifiers.trees.J48 "
                + "FILTER weka.filters.unsupervised.instance.Randomize "
                + "DATASET /Users/haha816/git/eats-what/src/main/resources/weka-3-8-4/data/iris.arff";

        String[] args = input.split(" ");
        if (args.length < 6) {
            System.out.println(WekaDemoService.usage());
            System.exit(1);
        }
        // parse command line
        String classifier = "";
        String filter = "";
        String dataset = "";
        Vector<String> classifierOptions = new Vector<String>();
        Vector<String> filterOptions = new Vector<String>();

        int i = 0;
        String current = "";
        boolean newPart = false;
        do {
            // determine part of command line
            if (args[i].equals("CLASSIFIER")) {
                current = args[i];
                i++;
                newPart = true;
            } else if (args[i].equals("FILTER")) {
                current = args[i];
                i++;
                newPart = true;
            } else if (args[i].equals("DATASET")) {
                current = args[i];
                i++;
                newPart = true;
            }

            if (current.equals("CLASSIFIER")) {
                if (newPart) {
                    classifier = args[i];
                } else {
                    classifierOptions.add(args[i]);
                }
            } else if (current.equals("FILTER")) {
                if (newPart) {
                    filter = args[i];
                } else {
                    filterOptions.add(args[i]);
                }
            } else if (current.equals("DATASET")) {
                if (newPart) {
                    dataset = args[i];
                }
            }

            // next parameter
            i++;
            newPart = false;
        } while (i < args.length);

        // everything provided?
        if (classifier.equals("") || filter.equals("") || dataset.equals("")) {
            System.out.println("Not all parameters provided!");
            System.out.println(WekaDemoService.usage());
            System.exit(2);
        }



        service.setClassifier(classifier,
                classifierOptions.toArray(new String[classifierOptions.size()]));
        service.setFilter(filter,
                filterOptions.toArray(new String[filterOptions.size()]));
        service.setTraining(dataset);
        service.execute();
        return service.toString();

    }


}
