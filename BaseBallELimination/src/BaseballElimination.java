

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.List;

public class BaseballElimination {
    public BaseballElimination(String filename)                    // create a baseball division from given filename in format specified below
    {
        In file = new In(filename);
        count = file.readInt();
        for (int i = 0; i < count; i++) {
            String name = file.readString();
            int win = file.readInt();
            int loss = file.readInt();
            int remain = file.readInt();
            int[] remains = new int[count];
            for (int j = 0; j < count; j++)
                remains[j] = file.readInt();
            teams.add(new team(name, win, loss, remain, remains));
            names.add(name);
        }

    }

    public int numberOfTeams()                        // number of teams
    {
        return count;
    }

    public Iterable<String> teams()                                // all teams
    {
        return names;
    }

    public int wins(String team)                      // number of wins for given team
    {
        if (names.contains(team)) {
            return teams.get(names.indexOf(team)).win;
        }
        throw new IllegalArgumentException();
    }

    public int losses(String team)                    // number of losses for given team
    {
        if (names.contains(team)) {
            return teams.get(names.indexOf(team)).loss;
        }
        throw new IllegalArgumentException();
    }

    public int remaining(String team)                 // number of remaining games for given team
    {
        if (names.contains(team)) {
            return teams.get(names.indexOf(team)).remain;
        }
        throw new IllegalArgumentException();
    }

    public int against(String team1, String team2)    // number of remaining games between team1 and team2
    {
        if (names.contains(team1) && names.contains(team2)) {
            return teams.get(names.indexOf(team1)).remains[names.indexOf(team2)];
        }
        throw new IllegalArgumentException();
    }

    public boolean isEliminated(String team)              // is given team eliminated?
    {
        if (!names.contains(team))
            throw new IllegalArgumentException();
        int index = names.indexOf(team);
        int v = 2 + count + (count - 2) * (count - 1) / 2;
        FlowNetwork network = new FlowNetwork(v);
        int dst=1;
        for (int i = 0; i < count; i++) {
            for (int j = i + 1; j < count; j++) {
                if (i == index || j == index) {
                    continue;
                }
                network.addEdge(new FlowEdge(0,dst,teams.get(i).remains[j]));
                network.addEdge(new FlowEdge(dst,v-1-count+i,Double.POSITIVE_INFINITY));
                network.addEdge(new FlowEdge(dst,v-1-count+j,Double.POSITIVE_INFINITY));
                dst++;
            }
        }
        for(int i=0;i<count;i++){
            if (i == index ) {
                continue;
            }
            if(wins(team)+remaining(team)-teams.get(i).win>0)
                network.addEdge(new FlowEdge(v-1-count+i,v-1,wins(team)+remaining(team)-teams.get(i).win));
        }
        FordFulkerson f=new FordFulkerson(network,0,v-1);
        for(int i=0;i<count;i++){
            if(f.inCut(v-1-count+i))
                return true;
        }
        return false;
    }

    public Iterable<String> certificateOfElimination(String team)  // subset R of teams that eliminates given team; null if not eliminated
    {
        if (!names.contains(team))
            throw new IllegalArgumentException();
        int index = names.indexOf(team);
        int v = 2 + count + (count - 2) * (count - 1) / 2;
        FlowNetwork network = new FlowNetwork(v);
        int dst=1;
        for (int i = 0; i < count; i++) {
            for (int j = i + 1; j < count; j++) {
                if (i == index || j == index) {
                    continue;
                }
                network.addEdge(new FlowEdge(0,dst,teams.get(i).remains[j]));
                network.addEdge(new FlowEdge(dst,v-1-count+i,Double.POSITIVE_INFINITY));
                network.addEdge(new FlowEdge(dst,v-1-count+j,Double.POSITIVE_INFINITY));
                dst++;
            }
        }
        for(int i=0;i<count;i++){
            if (i == index ) {
                continue;
            }
            if(wins(team)+remaining(team)-teams.get(i).win>0)
                network.addEdge(new FlowEdge(v-1-count+i,v-1,wins(team)+remaining(team)-teams.get(i).win));
        }
        FordFulkerson f=new FordFulkerson(network,0,v-1);
        List<String> list=new ArrayList<>();
        for(int i=0;i<count;i++){
            if(i==index)
                continue;
            if(f.inCut(v-1-count+i))
                list.add(teams.get(i).name);
        }
        return list;
    }

    private List<team> teams = new ArrayList<>();
    private List<String> names = new ArrayList<>();
    private int count;

    private class team {
        String name;
        int win;
        int loss;
        int remain;
        int[] remains;

        public team(String name, int win, int loss, int remain, int[] remains) {
            this.name = name;
            this.win = win;
            this.loss = loss;
            this.remain = remain;
            this.remains = remains;
        }
    }

    public static void main(String[] args) {
        BaseballElimination division = new BaseballElimination("teams7.txt");
        for (String team : division.teams()) {
            if (division.isEliminated(team)) {
                StdOut.print(team + " is eliminated by the subset R = { ");
                for (String t : division.certificateOfElimination(team)) {
                    StdOut.print(t + " ");
                }
                StdOut.println("}");
            } else {
                StdOut.println(team + " is not eliminated");
            }
        }
    }
}