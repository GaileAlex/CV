package ee.gaile.sync.proxy;

import ee.gaile.entity.proxy.ProxyEntity;
import ee.gaile.repository.proxy.ProxyRepository;
import ee.gaile.sync.SyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;

/**
 * Service for determining unknown countries from a proxy
 *
 * @author Aleksei Gaile
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CountrySyncService implements SyncService {
    private static final String IP_INFO_URL = "http://ipinfo.io/";

    private final ProxyRepository proxyRepository;
    private final RestTemplate restTemplate;

    /**
     * Sets the proxy country
     * <p>
     * A ban is possible if the number of requests is exceeded.
     * Proxy check is interrupted after ten rejections
     */
    @Override
    public void sync() {
        List<ProxyEntity> proxyEntities = proxyRepository.findAllWhereCountryUnknown();

        if (proxyEntities.isEmpty()) {
            return;
        }

        log.info("Start proxy country sync. Size lists is {} ", proxyEntities.size());

        int returnIfRequestIsBlocked = 0;

        for (ProxyEntity proxyEntity : proxyEntities) {
            if (proxyEntity.getCountry().equals("unknown")) {
                try {
                    ResponseEntity<String> listResponseEntity = restTemplate
                            .exchange(IP_INFO_URL + proxyEntity.getIpAddress(), HttpMethod.GET, null,
                                    new ParameterizedTypeReference<>() {
                                    });
                    String[] st = Objects.requireNonNull(listResponseEntity.getBody()).split("\",\\n");

                    String country = st[3].split(": \"")[1] + " " + st[2].split(": \"")[1];
                    proxyEntity.setCountry(country);
                    proxyRepository.save(proxyEntity);

                } catch (Exception e) {
                    log.info("proxy set country error for {} {}", proxyEntity.getIpAddress(), proxyEntity.getPort());
                    returnIfRequestIsBlocked++;
                    if (returnIfRequestIsBlocked == 10) {
                        return;
                    }
                }
            }
        }
    }
}
