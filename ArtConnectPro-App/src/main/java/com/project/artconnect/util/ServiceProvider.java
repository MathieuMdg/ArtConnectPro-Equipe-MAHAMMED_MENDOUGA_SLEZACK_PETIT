package com.project.artconnect.util;

import com.project.artconnect.dao.*;
import com.project.artconnect.persistence.*;
import com.project.artconnect.service.*;
import com.project.artconnect.service.impl.*;

public final class ServiceProvider {

    private static final ArtistService artistService;
    private static final ArtworkService artworkService;
    private static final GalleryService galleryService;
    private static final WorkshopService workshopService;
    private static final CommunityService communityService;

    static {

        ArtistDao artistDao =
                new JdbcArtistDao();

        ArtworkDao artworkDao =
                new JdbcArtworkDao();

        GalleryDao galleryDao =
                new JdbcGalleryDao();

        WorkshopDao workshopDao =
                new JdbcWorkshopDao();

        CommunityMemberDao communityMemberDao =
                new JdbcCommunityMemberDao();

        artistService =
                new JdbcArtistService(artistDao);

        artworkService =
                new JdbcArtworkService(artworkDao);

        galleryService =
                new JdbcGalleryService(galleryDao);

        workshopService =
                new JdbcWorkshopService(workshopDao);

        communityService =
                new JdbcCommunityService(communityMemberDao);
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