public class Main {
    public static class Item {
        public String name;
        public int id;
        public List<Item> children = new ArrayList<Item>();

        public Item name(String name) {
            this.name = name;
            return this;
        }

        public Item id(int id) {
            this.id = id;
            return this;
        }

        public Item child(Item child) {
            children.add(child);
            return this;
        }
    }


    public Item filter(Item tail, final String search) {
        Item result = new Item().id(tail.id).name(tail.name);

        if (tail.children.isEmpty() == false) {
            for (Item item : tail.children) {
                Item r2 = filter(item, search);
                if (r2 != null) {
                    result.child(r2);
                }
            }
            if (result.children.isEmpty() == false) {
                return result;
            }
        } else {
            if (tail.name.matches(".*" + search + ".*")) {
                return result;
            }
        }
        return null;
    }


    public static void main(String[] args) throws IOException, InstantiationException, IllegalAccessException {

        Item root = new Item().id(1).name("root");

        Item sub1 = new Item().id(2).name("sub1");
        Item sub2 = new Item().id(3).name("sub2");

        root.child(sub1).child(sub2);

        Item sub3 = new Item().id(4).name("sub4");
        Item sub4 = new Item().id(5).name("treasure");

        sub1.child(sub3).child(sub4);


        Item sub5 = new Item().id(6).name("sub5");
        Item sub6 = new Item().id(7).name("sub6");

        sub2.child(sub5).child(sub6);


        Item sub7 = new Item().id(8).name("sub7");
        Item sub8 = new Item().id(9).name("treasure");

        sub6.child(sub7).child(sub8);

        Main main = new Main();

        Item filtered = main.filter(root, "treasure");

        System.out.println(filtered);
    }
}
