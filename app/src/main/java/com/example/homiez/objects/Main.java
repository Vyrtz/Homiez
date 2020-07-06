package objects;


//only for testing 4 classes in objects
public class Main {

     public static void main(String []args){

            User a=new User("myd0",23,"male");
            Post p1=new Post("title",a,500,"myd", "house", "we are renting now");
            a.addPost(p1);
            System.out.println(a);
            User b=new User("myd1",23,"female");
            Post p2=new Post("title2",a,1000,"pambina","condo","we are so good here");
            a.addPost(p2);
            Post p3=new Post("title3", a,1300,"downtown","apartment","we are in downtonw" );
            a.addPost(p3);
            a.printPosts();

            a.deletePost(p2);
            System.out.println();
            a.printPosts();

            //method for user arraylist test sucessful



            //test post.arraylist
            User c=new User("myd2",24,"male");
            User e=new User("myd3",25,"female");
            p1.addAttached(b);
            p1.addAttached(c);
            p1.addAttached(e);
            p1.printAttachedUsers();
            p1.deleteAttached(c);
            p1.printAttachedUsers();
            //all worked well whatever delete which one.

     }





}
