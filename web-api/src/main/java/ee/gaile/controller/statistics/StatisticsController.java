package ee.gaile.controller.statistics;

import ee.gaile.models.statistics.VisitStatisticGraph;
import ee.gaile.service.statistics.StatisticsService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static ee.gaile.service.security.SecurityConfig.API_V1_PREFIX;

@RestController
@RequestMapping(path = API_V1_PREFIX + "/statistic")
@AllArgsConstructor
public class StatisticsController {
    private final StatisticsService statisticsService;

    private static final Logger ACCESS_LOG = LoggerFactory.getLogger("access-accounting-log");

    @GetMapping(path = "/graph/{fromDate}/{toDate}")
    public ResponseEntity<VisitStatisticGraph> getGraphData(@PathVariable(value = "fromDate") String fromDate,
                                                            @PathVariable(value = "toDate") String toDate) {
        return new ResponseEntity<>(statisticsService.getStatisticsGraph(fromDate, toDate), HttpStatus.OK);
    }

    @PostMapping(path = "/user")
    public void getUserSpy(HttpServletRequest request) {
        ACCESS_LOG.info("user name is {}, IP is {}, city is {}, country is {}",
                request.getHeader("user"),
                request.getHeader("userIP"),
                request.getHeader("userCity"),
                request.getHeader("userCountry"));

        statisticsService.setUserStatistics(request);
    }

}
