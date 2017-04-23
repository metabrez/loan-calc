package com.osa.loan.calc.web;

import com.google.common.collect.ImmutableList;
import com.osa.loan.calc.model.Person;
import com.osa.loan.calc.model.Verdict;
import com.osa.loan.calc.service.LoanService;
import com.osa.loan.calc.service.Questions;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import static org.apache.commons.lang3.tuple.ImmutablePair.of;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoanController {

    private final Questions questions;
    private final LoanService loanService;

    @ModelAttribute("civilStates")
    public List<Pair<String, Boolean>> getCivilStates() {
        return ImmutableList.of(
                of("Kawaler/Panna", false),
                of("Żonaty/Zamężna", true),
                of("W separacji", false),
                of("Wdowiec/Wdowa", false)
        );
    }

    @RequestMapping("/")
    public ModelAndView welcome() {
        return new ModelAndView("welcome", "person", new Person());
    }

    @ResponseBody
    @RequestMapping(path = "/question", produces = TEXT_PLAIN_VALUE, method = GET)
    public String getQuestion(@RequestParam String key) {
        return questions.getQuestion(key);
    }

    @ResponseBody
    @RequestMapping(path = "/count", produces = APPLICATION_JSON_VALUE, method = POST)
    public Verdict checkCreditworthiness(@RequestBody Person person) {
        return loanService.checkCreditworthiness(person);
    }
}