package ee.gaile.controller;

import ee.gaile.models.statistics.VisitStatisticGraph;
import ee.gaile.service.statistics.StatisticsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static ee.gaile.service.security.SecurityConfig.API_V1_PREFIX;

@Slf4j
@RestController
@RequestMapping(path = API_V1_PREFIX + "/statistic")
@AllArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    private static final Logger ACCESS_LOG = LoggerFactory.getLogger("access-accounting-log");

    @GetMapping(path = "/graph", produces = "application/json")
    public VisitStatisticGraph getGraphData() {
        return statisticsService.getStatisticsGraph();
    }

    @PostMapping(path = "/user", produces = "application/json")
    public void getUserSpy(HttpServletRequest request) {

        ACCESS_LOG.info("user name is {}, IP is {}, city is {}, country is {}",
                request.getHeader("user"), request.getHeader("userIP"), request.getHeader("userCity"),
                request.getHeader("userCountry"));

        statisticsService.setUserStatistics(request);
    }

}
