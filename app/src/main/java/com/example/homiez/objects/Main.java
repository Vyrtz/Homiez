package com.example.homiez.objects;


//only for testing 4 classes in objects
public class Main {

     public static void main(String []args){

            User myd0=new User("myd0","myd0",23,"male");
            Posting p1=new Posting("p1","p1",myd0,500,"myd", "house", "we are renting now");
            //once build a posting object it will add to its coressponded user.
            System.out.println(myd0);
            System.out.println(); //now myd0 contains p1.

            User myd1=new User("myd1","myd1",23,"female");

            Posting p2=new Posting("p2","p2",myd0,1000,"pambina","condo","we are so good here");
            //now myd0 contains p1,p2

            Posting p3=new Posting("p3","p3", myd0,1300,"downtown","apartment","we are in downtonw" );

            myd0.printPosts();


            System.out.println();
            myd0.deletePost(p3);
            myd0.printPosts();

            //method for user arraylist test sucessful


            System.out.println();
            //test post.arraylist
            User myd2=new User("myd2","myd2",24,"male");
            User myd3=new User("myd3","myd3",25,"female");
            p1.addAttached(myd1);
            p1.addAttached(myd2);
            p1.addAttached(myd3);
            p1.printAttachedUsers();
            System.out.println();
            p1.deleteAttached(myd2);
            p1.printAttachedUsers();
            //all worked well whatever delete which one.

     }





}
