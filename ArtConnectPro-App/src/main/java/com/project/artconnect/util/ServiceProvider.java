package com.project.artconnect.util;

import com.project.artconnect.dao.*;
import com.project.artconnect.persistence.*;
import com.project.artconnect.service.*;
import com.project.artconnect.service.impl.*;

/**
 * Service Provider to manage singleton instances of services.
 * <p>
 * Default mode is JDBC. Set -Dartconnect.useInMemory=true to switch back to the
 * demo services.
 */
public final class ServiceProvider {
    private static final boolean USE_IN_MEMORY = Boolean.parseBoolean(
            System.getProperty("artconnect.useInMemory", "false"));

    private static final ArtistService artistService;
    private static final ArtworkService artworkService;
    private static final GalleryService galleryService;
    private static final WorkshopService workshopService;
    private static final CommunityService communityService;

    static {
        if (USE_IN_MEMORY) {
            InMemoryArtistService inMemoryArtistService = new InMemoryArtistService();
            InMemoryArtworkService inMemoryArtworkService = new InMemoryArtworkService();
            InMemoryGalleryService inMemoryGalleryService = new InMemoryGalleryService();
            InMemoryWorkshopService inMemoryWorkshopService = new InMemoryWorkshopService();
            InMemoryCommunityService inMemoryCommunityService = new InMemoryCommunityService();

            inMemoryArtworkService.initData(inMemoryArtistService);
            inMemoryGalleryService.initData(inMemoryArtworkService);
            inMemoryWorkshopService.initData(inMemoryArtistService);
            inMemoryCommunityService.initData(inMemoryArtworkService);

            artistService = inMemoryArtistService;
            artworkService = inMemoryArtworkService;
            galleryService = inMemoryGalleryService;
            workshopService = inMemoryWorkshopService;
            communityService = inMemoryCommunityService;
        } else {
            ArtistDao artistDao = new JdbcArtistDao();
            ArtworkDao artworkDao = new JdbcArtworkDao();
            GalleryDao galleryDao = new JdbcGalleryDao();
            WorkshopDao workshopDao = new JdbcWorkshopDao();
            CommunityMemberDao communityMemberDao = new JdbcCommunityMemberDao();

            artistService = new JdbcArtistService(artistDao);
            artworkService = new JdbcArtworkService(artworkDao);
            galleryService = new JdbcGalleryService(galleryDao);
            workshopService = new JdbcWorkshopService(workshopDao);
            communityService = new JdbcCommunityService(communityMemberDao);
        }
    }

    private ServiceProvider() {
    }

    public static ArtistService getArtistService() {
        return artistService;
    }

    public static ArtworkService getArtworkService() {
        return artworkService;
    }

    public static GalleryService getGalleryService() {
        return galleryService;
    }

    public static WorkshopService getWorkshopService() {
        return workshopService;
    }

    public static CommunityService getCommunityService() {
        return communityService;
    }
}
