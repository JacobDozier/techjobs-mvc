package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "list")
public class ListController {

    static HashMap<String, String> columnChoices = new HashMap<>();

    public ListController () {
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");
    }

    @RequestMapping(value = "")
    public String list(Model model) {

        model.addAttribute("columns", columnChoices);

        return "list";
    }

    @RequestMapping(value = "values")
    public String listColumnValues(Model model, @RequestParam String column) {

        Integer numJobs;
        String stringNumJobs;

        if (column.equals("all")) {
            ArrayList<HashMap<String, String>> jobs = JobData.findAll();
            numJobs = jobs.size();
            stringNumJobs = numJobs.toString() + " Result(s)";
            model.addAttribute("title", "All Jobs");
            model.addAttribute("jobs", jobs);
            model.addAttribute("stringNumJobs", stringNumJobs);
            return "list-jobs";
        } else {
            ArrayList<String> items = JobData.findAll(column);
            numJobs = items.size();
            stringNumJobs = numJobs.toString() + " Result(s)";
            model.addAttribute("title", "All " + columnChoices.get(column) + " Values");
            model.addAttribute("column", column);
            model.addAttribute("items", items);
            model.addAttribute("stringNumJobs", stringNumJobs);
            return "list-column";
        }

    }

    @RequestMapping(value = "jobs")
    public String listJobsByColumnAndValue(Model model,
            @RequestParam String column, @RequestParam String value) {

        Integer numJobs;
        String stringNumJobs;

        ArrayList<HashMap<String, String>> jobs = JobData.findByColumnAndValue(column, value);
        numJobs = jobs.size();
        stringNumJobs = numJobs.toString() + " Result(s)";
        model.addAttribute("title", "Jobs with " + columnChoices.get(column) + ": " + value);
        model.addAttribute("jobs", jobs);
        model.addAttribute("stringNumJobs", stringNumJobs);

        return "list-jobs";
    }
}
