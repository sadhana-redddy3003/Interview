package org.example;

import java.util.HashMap;

package org.example;

public interface Router {
    void addRoute(String path,String result);

    String callRoute(String path);
}


class Node{
    HashMap<String, Node> children;

    String result;

    public  Node(){
        children = new HashMap<>();
        result = "";

    }
}

class RouterImp implements Router{
    Node head;

    public  RouterImp(){
        head = new Node();
    }


    @Override
    public void addRoute(String path, String result) {
        //router.addRoute("/bar/*/baz", "bar")

        if(path.isEmpty()) return;

        Node curr = head;

        String[] pathList = path.split("/");

        for(int i=1; i< pathList.length; i++){
            String currPath = pathList[i];

            if(!curr.children.containsKey(currPath))
                curr.children.put(currPath, new Node());

            curr = curr.children.get(currPath);

        }

        curr.result = result;

    }

    @Override
    public String callRoute(String path) {
        //Router.callRoute("/bar/bar/baz") -> "bar"
        if(path.isEmpty()) return null;
        Node curr = head;

        String[] pathList = path.split("/");

        for(int i=1; i< pathList.length; i++){
            String currPath = pathList[i];
            if(!curr.children.containsKey(currPath) && !curr.children.containsKey("*") )
                return  null;

            if(curr.children.containsKey(currPath)){
                curr = curr.children.get(currPath);
            }else{
                curr = curr.children.get("*");
            }

        }

        return curr.result.isEmpty()? null: curr.result ;

    }

    /*public  String callRouteRecursion(String[] pathList, int idx, ){



        return  null;
    }*/

}

package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        Router router = new RouterImp();
        /*router.addRoute("/bar", "result");
        router.addRoute("/bar", "result4");
        router.addRoute("/foo/bar", "result1");

        System.out.println(router.callRoute("/bar"));
        System.out.println(router.callRoute("/foo"));
        System.out.println(router.callRoute("/apple"));

        System.out.println(router.callRoute("/foo/bar"));
        System.out.println(router.callRoute("/foo/apple"));
        System.out.println(router.callRoute("/bar/foo"));

        System.out.println("==case2");*/




        router.addRoute("/bar/*/tree", "bar");
        router.addRoute("/bar/test/baz", "bar1");
        System.out.println(router.callRoute("/bar/test/baz"));

        System.out.println(router.callRoute("/bar/test/tree"));




    }
}

