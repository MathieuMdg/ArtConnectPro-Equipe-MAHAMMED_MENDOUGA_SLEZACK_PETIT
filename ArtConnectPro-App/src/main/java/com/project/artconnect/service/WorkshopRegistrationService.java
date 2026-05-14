package com.project.artconnect.service;

import com.project.artconnect.config.DatabaseConfig;
import com.project.artconnect.session.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class WorkshopRegistrationService {

    public boolean registerToWorkshop(int workshopId) {

        String sql = """
            INSERT INTO workshop_registration
            (member_id, workshop_id)
            VALUES (?, ?)
        """;

        try (
                Connection conn = DatabaseConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)
        ) {

            Integer memberId =
                    UserSession.getCurrentUser().getMemberId();

            ps.setInt(1, memberId);

            ps.setInt(2, workshopId);

            System.out.println("USER ID = " + memberId);
            System.out.println("WORKSHOP ID = " + workshopId);
            ps.executeUpdate();

            return true;

        } catch (Exception e) {

            e.printStackTrace();

            return false;
        }
    }
}