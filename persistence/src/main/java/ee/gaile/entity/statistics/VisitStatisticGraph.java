package ee.gaile.entity.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitStatisticGraph {
    private List<VisitStatisticsDTO> visitStatisticsDTOList;

    private Map<Date, Long> countedVisit;
}
