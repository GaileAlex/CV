package ee.gaile.sync;

import ee.gaile.entity.ProxyList;
import ee.gaile.repository.ProxyRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProxyListService {
    private static final Logger ERROR_LOG = LoggerFactory.getLogger("error-log");
    private final ProxyRepository proxyRepository;
    private final ProxyCheck proxyCheck;

    @Scheduled(fixedDelay = Long.MAX_VALUE)
    public void firstStartSyncService() {
        setAllProxy();
    }

    @Scheduled(cron = "${proxy.scheduled}")
    public void setAllProxy() {
        List<ProxyList> proxyLists = proxyRepository.findAllBySpeed();

        for (ProxyList proxyList : proxyLists) {
            if (!doFirstCheck(proxyList)) {
                continue;
            }
            proxyCheck.checkProxy(proxyList);
        }
    }

    private boolean doFirstCheck(ProxyList proxyList) {
        try {
            Files.deleteIfExists(Paths.get(proxyList.getId() + "_"
                    + proxyList.getIpAddress() + "_" + proxyList.getPort() + ".tmp"));
        } catch (IOException e) {
            ERROR_LOG.error("failed to delete file: " + proxyList.getIpAddress() + proxyList.getPort() + "tempFile.tmp");
        }

        if (proxyList.getNumberChecks() != null) {
            proxyList.setNumberChecks(proxyList.getNumberChecks() + 1);
        } else {
            proxyList.setNumberChecks(1);
        }

        if (proxyList.getFirstChecked() == null) {
            proxyList.setFirstChecked(LocalDateTime.now());
            proxyList.setAnonymity("High anonymity");
            proxyRepository.save(proxyList);
        }

        if (proxyList.getUptime() != null && proxyList.getNumberUnansweredChecks() != null
                && proxyList.getUptime() < 20 && proxyList.getNumberUnansweredChecks() > 200) {
            proxyRepository.delete(proxyList);
            return false;
        }
        return true;
    }

}
