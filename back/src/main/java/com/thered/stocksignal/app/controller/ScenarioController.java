package com.thered.stocksignal.app.controller;

import com.thered.stocksignal.service.scenario.ScenarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ScenarioController {

    private final ScenarioService scenarioService;

    //
}
