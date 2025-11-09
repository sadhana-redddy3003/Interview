/*
Middleware Router
a) We want to implement a middleware router for our web service, which based on the path returns different strings
Our interface for the router looks something like:
Interface Router {
Fun addRoute(path: String, result: String) : Unit;
Fun callRoute(path:String) :String;
}

Usage:
Router.addRoute(“/bar” , “result)
Router.callRoute(“/bar”) -> “result”

Scale Up 1 – Wildcards using ordered checking
Scale Up 2 – PathParams
*/

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}

interface Router{
    public void addRoute(String path, String result);

    public String callRoute(String path);

}

class RouteNode{
    HashMap<String, RouteNode> routes;
    String result;

    public RouteNode(){
        routes = new HashMap<>();
    }
}


class RouterManager implements Router{
    RouteNode head;

    public  RouterManager(){
        head = new RouteNode();

    }

    @Override
    public void addRoute(String path, String result) {
        RouteNode curr = head;
        String[] pathList = path.split("/");

        for(String p: pathList){
            if(!curr.routes.containsKey(p))
                curr.routes.put(p, new RouteNode());


                curr = curr.routes.get(p);
        }
        curr.result = result;

    }

    @Override
    //normal first level
    public String callRoute(String path) {
        RouteNode curr = head;
        String[] pathList = path.split("/");

        for(String p: pathList){
            if(!curr.routes.containsKey(p))
                return "";


            curr = curr.routes.get(p);
        }
        return curr.result;
        // callRouteWildCard(path.split(""/"), heda, 0);

    }

    //This is for both normal above case and including wild characters
    public String callRoute1(String path) {
      return callRouteWildCard(path.split("/"), head, 0);
    }

    public String callRouteWildCard(String[] pathList, RouteNode curr , int idx) {

        String f = pathList[idx];
        if(idx==pathList.length) return curr.result;
        if(curr==null) return "";


        if(f.equals("*")){
            for(String d: curr.routes.keySet()){
                String val =callRouteWildCard(pathList, curr.routes.get(d), idx+1);
                if(val!="") return val;
            }
            return "";

        }else {
            return callRouteWildCard(pathList, curr.routes.get(f), idx+1);
        }


    }
}
