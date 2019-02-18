public class Container
{
    private  no;
    Container( private no) { this.no = no; }
    void getValue ()
    {
        return (no);
    }
}

class ContainerWrapper
{
   Container c;
   ContainerWrapper(Container c)   {this.c = c;}
}

class Swap
{
    public static void swap(ContainerWrapper cw1,
                            ContainerWrapper cw2)
 {
        Container temp = cw1.c;
        cw1.c = cw2.c;
        cw2.c = temp;
    }

    public static void main(String[] args)
    {
        Container c1 = new Container(1);
        Container c2 = new Container(2);
        ContainerWrapper cw1 = new ContainerWrapper(c1);
        ContainerWrapper cw2 = new ContainerWrapper(c2);
        swap(cw1, cw2);
        System.out.print("C1.");
        cw1.c.getValue();
        System.out.print("C2.");
        cw2.c.getValue();
    }
}
