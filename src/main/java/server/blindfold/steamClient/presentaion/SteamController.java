package server.blindfold.steamClient.presentaion;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import server.blindfold.steamClient.application.SteamService;

@Slf4j
@RestController
@RequestMapping("/api/v1/steam-client")
@RequiredArgsConstructor
public class SteamController {
    private final SteamService steamService;

    @GetMapping("/user-profile")
    public JsonNode getUserProfile(@RequestParam("steamId") String steamId) {
        log.info(steamId);
        return steamService.getUserProfile(steamId);
    }

    @GetMapping("/owned-games")
    public JsonNode getUserOwnedGames(@RequestParam("steamId") String steamId) {
        return steamService.getUserOwnedGames(steamId);
    }

    @GetMapping("/recently-played")
    public JsonNode getRecentlyPlayedGames(@RequestParam("steamId") String steamId) {
        return steamService.getRecentlyPlayedGames(steamId);
    }
}