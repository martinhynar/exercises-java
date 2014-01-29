package martinhynar.baseballelimination;

import stanford.algs4.FlowEdge;
import stanford.algs4.FlowNetwork;
import stanford.algs4.FordFulkerson;
import stanford.stdlib.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 * @author Martin Hynar
 */
public class BaseballElimination {
    private final int numberOfTeams;
    private HashMap<String, Team> teamNames;
    private FlowNetwork flowNetwork;
    private int sink;

    private static class Team {
        private int vertex;
        private int wins;
        private int losses;
        private int remaining;
        private HashMap<Integer, Integer> remainingAgainst = new HashMap<Integer, Integer>();
        private List<String> certificate;

        @Override
        public String toString() {
            return String.format("Stats [vertex=%s, wins=%s, losses=%s, remaining=%s, remainingAgainst=%s]%n", vertex, wins,
                    losses, remaining, remainingAgainst);
        }

    }

    /**
     * create a baseball division from given filename in format specified below
     */
    public BaseballElimination(String filename) {
        // 4
        // wins loss left At Ph NY Mo
        // Atlanta 83 71 8 0 1 6 1
        // Philadelphia 80 79 3 1 0 0 2
        // New_York 78 78 6 6 0 0 0
        // Montreal 77 82 3 1 2 0 0
        In in = new In(filename);
        int noOfVertices = 0;
        // Read numer of teams in the table
        numberOfTeams = in.readInt();
        in.readLine();
        teamNames = new HashMap<String, Team>();
        int gameCombinations = getGameCombinations(numberOfTeams);
        noOfVertices += numberOfTeams + gameCombinations + 2;
        flowNetwork = new FlowNetwork(noOfVertices);
        sink = noOfVertices - 1;
        // Read team lines and parse them
        for (int teamOrder = 0, vertex = 1; teamOrder < numberOfTeams; teamOrder++) {
            String line = in.readLine();
            String[] team = line.trim().split("\\s+");
            Team tstats = new Team();
            String teamName = team[0];
            teamNames.put(teamName, tstats);
            tstats.vertex = teamOrder + gameCombinations + 1;
            tstats.wins = Integer.parseInt(team[1]);
            tstats.losses = Integer.parseInt(team[2]);
            tstats.remaining = Integer.parseInt(team[3]);
            for (int oponent = teamOrder + 1; oponent < numberOfTeams; oponent++) {
                int gamesAgainst = Integer.parseInt(team[4 + oponent]);
                FlowEdge fe = new FlowEdge(0, vertex, gamesAgainst);
                tstats.remainingAgainst.put(oponent + gameCombinations + 1, gamesAgainst);
                flowNetwork.addEdge(fe);
                fe = new FlowEdge(vertex, teamOrder + gameCombinations + 1, Double.POSITIVE_INFINITY);
                flowNetwork.addEdge(fe);
                fe = new FlowEdge(vertex, oponent + gameCombinations + 1, Double.POSITIVE_INFINITY);
                flowNetwork.addEdge(fe);
                vertex++;
            }
        }

        // Add zero capacity edges from team to sink
        // FlowEdge[] teamToSink = new FlowEdge[numberOfTeams];
        // for (int i = 0; i < teamToSink.length; i++) {
        // teamToSink[i] = new FlowEdge(i + gameCombinations + 1, sink, 0);
        // flowNetwork.addEdge(teamToSink[i]);
        // }
        // System.out.println(flowNetwork);
        // System.out.println("");
        // for (Entry<String, Team> targetTeamEntry : teamNames.entrySet()) {
        // Team targetTeam = targetTeamEntry.getValue();
        // int chance = targetTeam.wins + targetTeam.remaining;
        //
        // for (Entry<String, Team> teamEntry : teamNames.entrySet()) {
        // Team team = targetTeamEntry.getValue();
        // int winPotential = chance - team.wins;
        // if (winPotential < 0) {
        // targetTeam.certificate.add(teamEntry.getKey());
        // continue;
        // }
        // FlowEdge fe = teamToSink[team.vertex - (gameCombinations + 1)];
        // fe = new FlowEdge(team.vertex, sink, winPotential);
        // }
        // if (isEliminated(targetTeamEntry.getKey())) {
        // continue;
        // }
        // FordFulkerson ff = new FordFulkerson(flowNetwork, 0, sink);
        // for (Entry<String, Team> teamEntry : teamNames.entrySet()) {
        // Team team = teamEntry.getValue();
        // if (ff.inCut(team.vertex)) {
        // targetTeam.certificate.add(teamEntry.getKey());
        // }
        // }
        // }
    }

    /**
     * number of teams
     */
    public int numberOfTeams() {
        return this.numberOfTeams;
    }

    /**
     * all teams
     */
    public Iterable<String> teams() {
        return teamNames.keySet();
    }

    /**
     * number of wins for given team
     */
    public int wins(String team) {
        teamExists(team);
        return teamNames.get(team).wins;
    }

    /**
     * number of losses for given team
     */
    public int losses(String team) {
        teamExists(team);
        return teamNames.get(team).losses;
    }

    /**
     * number of remaining games for given team
     */
    public int remaining(String team) {
        teamExists(team);
        return teamNames.get(team).remaining;
    }

    /**
     * number of remaining games between team1 and team2
     */
    public int against(String team1, String team2) {
        teamExists(team1);
        teamExists(team2);
        if (team1.equals(team2)) {
            return 0;
        }
        Team teamA = teamNames.get(team1);
        Team teamB = teamNames.get(team2);
        if (teamA.vertex < teamB.vertex) {
            return teamA.remainingAgainst.get(teamB.vertex);
        } else {
            return teamB.remainingAgainst.get(teamA.vertex);
        }
    }

    /**
     * is given team eliminated?
     */
    public boolean isEliminated(String team) {
        teamExists(team);
        calculateElimination(team);
        return !teamNames.get(team).certificate.isEmpty();
    }

    /**
     * subset R of teams that eliminates given team; null if not eliminated
     */
    public Iterable<String> certificateOfElimination(String team) {
        teamExists(team);
        calculateElimination(team);
        if (!isEliminated(team)) {
            return null;
        }
        return teamNames.get(team).certificate;
    }

    private int getGameCombinations(int teams) {
        return (int) ((teams * (teams - 1)) / 2);
    }

    private void calculateElimination(String teamName) {
        Team targetTeam = teamNames.get(teamName);
        if (targetTeam.certificate != null) {
            return;
        }

        targetTeam.certificate = new ArrayList<String>();
        int winsAndRemaining = targetTeam.wins + targetTeam.remaining;

        FlowNetwork fullFF = new FlowNetwork(sink + 1);
        for (FlowEdge fed : flowNetwork.edges()) {
            FlowEdge ffed = new FlowEdge(fed.from(), fed.to(), fed.capacity());
            fullFF.addEdge(ffed);
        }
        for (Entry<String, Team> teamEntry : teamNames.entrySet()) {
            Team team = teamEntry.getValue();
            int winPotential = winsAndRemaining - team.wins;
            if (winPotential < 0) {
                targetTeam.certificate.add(teamEntry.getKey());
                continue;
            }
            FlowEdge fe = new FlowEdge(team.vertex, sink, winPotential);
            fullFF.addEdge(fe);
        }
        if (isEliminated(teamName)) {
            return;
        }
        FordFulkerson ff = new FordFulkerson(fullFF, 0, sink);
        for (Entry<String, Team> teamEntry : teamNames.entrySet()) {
            Team team = teamEntry.getValue();
            if (ff.inCut(team.vertex)) {
                targetTeam.certificate.add(teamEntry.getKey());
            }
        }
    }

    private void teamExists(String name) {
        if (teamNames.get(name) == null) {
            throw new IllegalArgumentException();
        }
    }
}
