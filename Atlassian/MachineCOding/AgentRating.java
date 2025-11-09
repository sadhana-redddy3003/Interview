/**
Customer Satisfaction
a) Imagine we have a customer support ticketing system. The system allows customers to rate the support agent out of 5. To start with, write a function which accepts a rating, and another which will show me all of the agents and the average ratingeach one has received, ordered highest to lowest.
b) Currently your solution does not account for what happens if two agents have the same average rating. What options are there for handling ties and how can we implement that in code?
c) Now I want to be able to see who the best agents are each month. Change the implementation so I can get that information.
d) Write a new function that will allow me to export of each agent’s average ratings per month. You can export in any format you like- for example csv,json or xml.
e) Make it return the average ratings unsorted./ Make it return the total rating for each agent without the average
**/


import java.util.*;

public class AgentRatingSystem {
    public static void main(String[] args) {
        CustomerSupport cs = new CustomerSupport();

        // (a) Add sample ratings for different months
        cs.rate(101, 5, "2025-10");
        cs.rate(102, 4, "2025-10");
        cs.rate(101, 3, "2025-11");
        cs.rate(103, 5, "2025-11");
        cs.rate(102, 5, "2025-11");
        cs.rate(101, 4, "2025-11");
        cs.rate(104, 5, "2025-11");

        System.out.println("===== (a) All Agents with Average Ratings =====");
        cs.showAverageRatings();  // highest → lowest

        System.out.println("\n===== (b) Tie Handling (secondary sort by agentId) =====");
        // Already handled by comparator (see showAverageRatings)

        System.out.println("\n===== (c) Best Agents for November =====");
        cs.showBestAgents("2025-11");

        System.out.println("\n===== (d) CSV Export =====");
        System.out.println(cs.exportCsv());

        System.out.println("\n===== (e1) Unsorted Average Ratings =====");
        cs.showAverageRatingsUnsorted();

        System.out.println("\n===== (e2) Total Ratings per Agent =====");
        cs.showTotalRatings();
    }
}

/* ---------------------------------------------------------------------- */
/* Rating class: holds cumulative sum and count for computing averages    */
/* ---------------------------------------------------------------------- */
class Rating {
    double sum;
    int count;

    public void addRating(double val) {
        sum += val;
        count++;
    }

    public double getAverage() {
        return count == 0 ? 0.0 : sum / count;
    }

    public double getTotal() {
        return sum;
    }
}

/* ---------------------------------------------------------------------- */
/* CustomerSupport system implementation                                 */
/* ---------------------------------------------------------------------- */
class CustomerSupport {
    // (1) Month-level data: agentId → (month → Rating)
    private final HashMap<Integer, HashMap<String, Rating>> agentMonthlyRatings;

    // (2) Overall data: agentId → Rating (for O(1) total/average)
    private final HashMap<Integer, Rating> overallRatings;

    public CustomerSupport() {
        agentMonthlyRatings = new HashMap<>();
        overallRatings = new HashMap<>();
    }

    /* (a) Accept a rating for an agent in a given month */
    public void rate(int agent, double rating, String month) {
        if (rating < 1 || rating > 5)
            throw new IllegalArgumentException("Rating must be between 1 and 5");

        // Update monthly rating
        agentMonthlyRatings
                .computeIfAbsent(agent, k -> new HashMap<>())
                .computeIfAbsent(month, k -> new Rating())
                .addRating(rating);

        // Update overall rating (O(1))
        overallRatings
                .computeIfAbsent(agent, k -> new Rating())
                .addRating(rating);
    }

    /* (a) & (b) Show average rating per agent (sorted high→low, tie = smaller agentId) */
    public void showAverageRatings() {
        List<Integer> agents = new ArrayList<>(overallRatings.keySet());
        agents.sort((a, b) -> {
            double avgA = overallRatings.get(a).getAverage();
            double avgB = overallRatings.get(b).getAverage();
            int cmp = Double.compare(avgB, avgA);  // descending
            if (cmp != 0) return cmp;
            return Integer.compare(a, b);          // tie-breaker
        });

        System.out.println("Agent\tAverage");
        for (int id : agents) {
            System.out.printf("%d\t%.2f%n", id, overallRatings.get(id).getAverage());
        }
    }

    /* (c) Show best agents for a given month */
    public void showBestAgents(String month) {
        List<Integer> agents = new ArrayList<>(agentMonthlyRatings.keySet());
        agents.sort((a, b) -> {
            double avgA = getAverageForMonth(a, month);
            double avgB = getAverageForMonth(b, month);
            int cmp = Double.compare(avgB, avgA);
            if (cmp != 0) return cmp;
            return Integer.compare(a, b);
        });

        System.out.println("Agent\tAverage(" + month + ")");
        for (int id : agents) {
            System.out.printf("%d\t%.2f%n", id, getAverageForMonth(id, month));
        }
    }

    /* (d) Export all monthly averages as CSV */
    public String exportCsv() {
        StringBuilder sb = new StringBuilder("Agent,Month,Average\n");
        for (int agent : agentMonthlyRatings.keySet()) {
            for (String month : agentMonthlyRatings.get(agent).keySet()) {
                double avg = agentMonthlyRatings.get(agent).get(month).getAverage();
                sb.append(agent)
                  .append(",")
                  .append(month)
                  .append(",")
                  .append(String.format("%.2f", avg))
                  .append("\n");
            }
        }
        return sb.toString();
    }

    /* (e1) Unsorted average ratings */
    public void showAverageRatingsUnsorted() {
        System.out.println("Agent\tAverage");
        for (int agent : overallRatings.keySet()) {
            System.out.printf("%d\t%.2f%n", agent, overallRatings.get(agent).getAverage());
        }
    }

    /* (e2) Total ratings per agent */
    public void showTotalRatings() {
        System.out.println("Agent\tTotalRating");
        for (int agent : overallRatings.keySet()) {
            System.out.printf("%d\t%.2f%n", agent, overallRatings.get(agent).getTotal());
        }
    }

    /* ---------------- Helper ---------------- */
    private double getAverageForMonth(int agent, String month) {
        Rating r = agentMonthlyRatings.getOrDefault(agent, new HashMap<>()).get(month);
        return (r == null) ? 0.0 : r.getAverage();
    }
}
