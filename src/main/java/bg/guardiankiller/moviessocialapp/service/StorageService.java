package bg.guardiankiller.moviessocialapp.service;

import java.util.Collection;

public interface StorageService {

    void downloadFromTMDB(Collection<String> paths);

    String retrieveURL(String path);
}
