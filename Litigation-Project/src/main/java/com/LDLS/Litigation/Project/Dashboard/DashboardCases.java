package com.LDLS.Litigation.Project.Dashboard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardCases {
    private long activeCases;
    private long pendingCases;
    private long closedCases;



}