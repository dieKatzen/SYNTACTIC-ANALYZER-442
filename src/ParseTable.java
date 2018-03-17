import java.io.*;
import java.util.*;

import static java.util.Arrays.copyOfRange;
public class ParseTable {

    Productions [][] ll1table;
    Tree ast = new Tree();

    Map <String, ArrayList<String []>> grammarMap = new HashMap<String, ArrayList<String []>>();
    Map <String, ArrayList<String []>> grammarMapFirst = new HashMap<String, ArrayList<String []>>();
    Map <String, HashSet> firstMap = new HashMap<String, HashSet>();
    Map <String, HashSet> followMap = new HashMap<String, HashSet>();
    Map <String, HashSet> followMapNew = new HashMap<String, HashSet>();
    HashSet <String> unresolvedFollows = new HashSet<String>();
    //0 for first 1 for follow

    ArrayList<String> successfullyParsed = new ArrayList<String>();

    Set terminals = new HashSet();
    Set non_terminals = new HashSet();
    Set epsilons = new HashSet();
    HashSet<String[]> visitedFirst = new HashSet<String[]>();

    private static void printList(List<String[]> myList) {
        for (String [] arrlist: myList) {
            System.out.println(Arrays.toString(arrlist));
        }
    }

    public void loadTransitionTableCSV (){
        String csvFile = "grammar.txt";
        BufferedReader br = null;
        String line = "";
        String [] lineArr;
        String cvsSplitBy = " +";

        try {

            br = new BufferedReader(    new FileReader(csvFile));
            int row = 0;
            while ((line = br.readLine()) != null) {

                lineArr = line.split(cvsSplitBy);
                if(grammarMap.get(lineArr[0])== null){
                    ArrayList <String []> als =  new ArrayList<String[]>();
                    als.add(copyOfRange(lineArr, 2, lineArr.length));
                    grammarMap.put(lineArr[0],als);
                }else{
                    grammarMap.get(lineArr[0]).add(copyOfRange(lineArr, 2, lineArr.length));

                }
                terminalOrNonTerminal(lineArr);
                row++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void terminalOrNonTerminal(String [] args){
        for(int i=0;i<args.length; i++){
            if(i == 0){
                non_terminals.add(args[i]);
            }else if (i>1){
                terminals.add(args[i]);
            }
        }
    }

    public void deriveFirst() {

        Iterator nti = non_terminals.iterator();

        while(nti.hasNext()){
            deriveFirstHelper(nti.next().toString());
            visitedFirst.clear();
        }
    }

    public void deriveFirstHelper(String s) {
        Iterator<String []> cmi = grammarMap.get(s).iterator();
        HashSet <String> holder = new HashSet<String>();

        while(cmi.hasNext()){
            String[] array = cmi.next();
            findFirstSet(s,array,s);
        }
    }

    public void findEpsilons(){

        Iterator nti = non_terminals.iterator();

        while(nti.hasNext()){
            findEpsilonHelper(nti.next().toString());
        }

    }

    public void findEpsilonHelper(String s) {
        Iterator<String []> cmi = grammarMap.get(s).iterator();

        while(cmi.hasNext()){
            String[] array = cmi.next();
            if(array[0].equals("EPSILON")){
                if(firstMap.get(s)==null){
                    HashSet hs = new HashSet();
                    hs.add(array[0]);
                    firstMap.put(s, hs);
                }else{
                    firstMap.get(s).add(array[0]);
                }
            }
        }
    }


    public String findFirstSet(String source, String [] s,String c) {

        for(int i = 0; i < s.length;i++){

            if(s[i].toString().equals("EPSILON")){
                return "EPSILON";
            }
            if(terminals.contains(s[i].toString())){
                if(firstMap.get(source)==null){
                    HashSet hs = new HashSet();
                    hs.add(s[i]);
                    firstMap.put(source, hs);
                    return s[i].toString();
                }else{
                    firstMap.get(source).add(s[i]);
                    return s[i].toString();
                }
            }

            if(non_terminals.contains(s[i].toString())){

                Iterator cmi = grammarMap.get(s[i]).iterator();
                HashSet <String> holder = new HashSet<String>();

                while(cmi.hasNext()){
                    String [] prodAr = (String [])cmi.next();
                    holder.add(findFirstSet(source,prodAr,s[i]));
                };

                if(!holder.contains("EPSILON")){
                    return null;
                }else if (i == s.length-1){
                    if(firstMap.get(c)==null){
                        HashSet hs = new HashSet();
                        hs.add("EPSILON");
                        firstMap.put(source, hs);
                    }else{
                        firstMap.get(c).add("EPSILON");
                    }
                    return("EPSILON");
                };
            }
        }
        return null;
    }

    public void deriveFollow() {

        Iterator nti = non_terminals.iterator();

        while(nti.hasNext()){
            deriveFollowHelper(nti.next().toString());
            visitedFirst.clear();
        }
    }
    public void deriveFollowHelper(String s) {

        Iterator nti = non_terminals.iterator();

        while(nti.hasNext()){
            deriveFollowHelper0(s, nti.next().toString());
            visitedFirst.clear();
        }
    }

    public void deriveFollowHelper0(String s, String c) {
        Iterator<String []> cmi = grammarMap.get(c).iterator();

        while(cmi.hasNext()){
            String[] array = cmi.next();
            findFollowSet(s, array,c);
        }
    }

    public void findFollowSet(String source, String [] s,String c) {
        boolean match = false;
        for(int i = 0; i < s.length;i++){
            if(match) {
                if (non_terminals.contains(s[i])) {


                    if (followMap.get(source) == null) {
                        followMap.put(source, firstMap.get(s[i]));
                    } else {
                        followMap.get(source).addAll(firstMap.get(s[i]));
                    }

                    if (!firstMap.get(s[i]).contains("EPSILON")) {
                        return;
                    }

                } else if (terminals.contains(s[i])) {
                    if (followMap.get(source) == null) {
                        HashSet<String> hs = new HashSet<String>();
                        hs.add(s[i]);
                        followMap.put(source, hs);
                        return;
                    } else {
                        followMap.get(source).add(s[i]);
                        return;
                    }
                }

                if(i == s.length-1){
                    if(followMap.get(source)==null){
                        HashSet <String> hs = new HashSet<String>();
                        hs.add("FOLLOW-"+c);
                        followMap.put(source, hs);
                    }else{
                        HashSet <String> hs = new HashSet<String>();
                        hs.add("FOLLOW-"+c);
                        followMap.get(source).add(hs);
                    }
                    unresolvedFollows.add(c);
                }
            }


            if(s[i].equals(source)){
                match = true;
                if(i == s.length-1){
                    if(followMap.get(source)==null){
                        HashSet <String> hs = new HashSet<String>();
                        hs.add("FOLLOW-"+c);
                        followMap.put(source, hs);
                    }else{
                        HashSet <String> hs = new HashSet<String>();
                        hs.add("FOLLOW-"+c);
                        followMap.get(source).add(hs);
                    }
                }
                unresolvedFollows.add(c);
            }
        }
    }

    public void printFirstMap() {


        PrintWriter out;
        try {
                out = new PrintWriter("FirstSet.txt");


                for (Map.Entry<String, HashSet> entry : firstMap.entrySet())
            {
                out.print(entry.getKey() +"  >>> {");
                Iterator hsit = entry.getValue().iterator();
                while(hsit.hasNext()){
                    out.print(hsit.next().toString()+ ", ");
                }
                out.println(" } ");
            }
            out.close();
            }catch(Exception e){
                System.out.println(e);
        }
    }

    public void removeEpsilonFollowMap() {

        for (Map.Entry<String, HashSet> entry : followMap.entrySet())
        {
            entry.getValue().remove("EPSILON");
        }
    }

    public void printFollowMap() {

        for (Map.Entry<String, HashSet> entry : followMap.entrySet())
        {
            System.out.print(entry.getKey() +"  >>> {");
            Iterator hsit = entry.getValue().iterator();
            while(hsit.hasNext()){
                System.out.print(hsit.next().toString()+ ", ");
            }
            System.out.println(" } ");
        }
    }

    public void printFollowMapNew() {

        PrintWriter out;
        try {
            out = new PrintWriter("FollowSet.txt");

            for (Map.Entry<String, HashSet> entry : followMapNew.entrySet()) {
                out.print(entry.getKey() + "  >>> {");
                Iterator hsit = entry.getValue().iterator();
                while (hsit.hasNext()) {
                    out.print(hsit.next().toString() + ", ");
                }
                out.println(" } ");
            }
            out.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }


    public void resolveFollowMap0() {


        initfollowMapNew(followMapNew);

        for (Map.Entry<String, HashSet> entry : followMap.entrySet()) {

            HashSet<String> visitedF = new HashSet<String>();
            String s = entry.getKey();
            HashSet<String> followVisited = new HashSet<String>();
            boolean hasFollow = false;

            resolveFollowMapHelper(followVisited, s,s,s);

        }

    }

        public void resolveFollowMapHelper (HashSet < String > followVisited, String co, String c, String s){

            if (hashContainsEqual(followVisited, co)) {
                return;
            } else {
                followVisited.add(c);
                followVisited.add(co);
            }


            Iterator fmi = followMap.get(c).iterator();
            while (fmi.hasNext()) {

                String curr = fmi.next().toString();
                if (hashContainsEqual(followVisited, curr)) {
                    continue;
                }
                if (curr.contains("FOLLOW-")) {
                    if (curr.contains("[")) {
                        resolveFollowMapHelper(followVisited, curr, curr.substring(8, curr.length() - 1), s);
                    } else {
                        resolveFollowMapHelper(followVisited, curr, curr.substring(7, curr.length()), s);
                    }
                } else {
                    if (followMapNew.get(s) == null) {
                        HashSet<String> hs = new HashSet<String>();
                        hs.add(curr);
                        followMapNew.put(s, hs);
                    } else {
                        followMapNew.get(s).add(curr);
                    }
                }
            }
            return;
        }


    public void initfollowMapNew(Map m){
        Iterator nti = non_terminals.iterator();
        while(nti.hasNext()){
            m.put(nti.next().toString(),new HashSet<String>());
        }

    }

    public boolean hashContainsEqual(HashSet <String> visited, String c){
        boolean result = false;
       Iterator vsi = visited.iterator();
       while(vsi.hasNext()){
           if(vsi.next().toString().equals(c)){
               return true;
           }
       }
        return result;
    }

    public void initLL1table(){
        Object [] nta = non_terminals.toArray();
        Object [] ta = terminals.toArray();
        ll1table = new Productions [nta.length+1] [ta.length+1];
        for(int i=1; i<nta.length+1; i++){
            ll1table[i][0] = new Productions(nta[i-1].toString());
        }
        for(int i=1; i<ta.length+1; i++){
            ll1table[0][i] = new Productions(ta[i-1].toString());
            System.out.print(ll1table[0][i].labelName+" ");
        }
        for(int i=1; i<nta.length+1; i++){
            for(int j =1; j<ta.length+1; j++){
                ll1table[i][j] = new Productions("");
            }
        }

    }

    public void fillLL1table(){

        for(int i=1; i<non_terminals.toArray().length+1; i++){
            ArrayList <String []> arl = grammarMap.get(ll1table[i][0].labelName);
            Iterator<String []> arli= arl.iterator();
            while(arli.hasNext()){
                String [] arlia = arli.next();
                if(arlia[0].equals("EPSILON")){
                    insertIntoTable(followMapNew.get(ll1table[i][0].labelName),arlia,i);
                }else{
                    if(terminals.contains(arlia[0])) {
                        HashSet <String> hs = new HashSet<String>();
                        hs.add(arlia[0]);
                        insertIntoTable(hs, arlia, i);
                    }else{
                        insertIntoTable(firstMap.get(arlia[0]), arlia, i);
                    }
                };
            }
        }
    }

    public void insertIntoTable(HashSet<String> hs,String[]arlia, int i ){
        int length = terminals.toArray().length+1;
        for(int j = 1; j<length ;j++){
            if(hs.contains(ll1table[0][j].labelName)){
                ll1table[i][j] = new Productions(arlia);
                hs.remove(ll1table[0][j].labelName);
                if(hs.isEmpty()){
                    return;
                }
            }
        }
    }

    public void printTable (){
        PrintWriter out;
        try {
            out = new PrintWriter("predictTable.txt");
            for(Productions parr [] : ll1table){
                for(int i= 0; i<parr.length; i++)
                    if(i>0) {
                        if (parr[i].isLabel) {
                            out.print(String.format("%-65s %10s", "   " + parr[i].labelName, "|"));
                        } else if (parr[i].getGrammar() != null) {
                            out.print(String.format("%-65s %10s", "   " + parr[0].labelName + " => " + Arrays.toString(parr[i].getGrammar()), "|"));
                        }
                    }else{
                        if (parr[i].isLabel) {
                            out.print(String.format("%65s %10s", "   " + parr[i].labelName, "|"));
                        }
                    }

                out.println();
                out.println();
            }
            out.close();
        }catch(Exception e){
            System.out.println(e);
        }
    }


    public static void main(String [] args){
        ParseTable tt = new ParseTable();
        tt.loadTransitionTableCSV();


        tt.terminals.removeAll(tt.non_terminals);
        tt.terminals.remove("");

        System.out.println("Terminals");
        System.out.println(Arrays.toString(tt.terminals.toArray()));

        System.out.println("non-Terminals");
        System.out.println(Arrays.toString(tt.non_terminals.toArray()));

        System.out.println("");
        System.out.println("");
        System.out.println(Arrays.toString(tt.grammarMap.get("idnestRepeat").get(0)));
        System.out.println("");


//        tt.printContentMap();

        System.out.println("");

        tt.findEpsilons();
//

        tt.deriveFirst();

        System.out.println("FirstSets");
        System.out.println("");
        System.out.println("");
        System.out.println("");

//        tt.printContentMapFirst();
        tt.printFirstMap();
        System.out.println("");

        System.out.println("FollowSets");
        System.out.println("");
        System.out.println("");
        System.out.println("");

        tt.deriveFollow();
//        tt.deriveFollowHelper("idnestRepeat");
        tt.removeEpsilonFollowMap();
        tt.printFollowMap();


        tt.resolveFollowMap0();
        tt.printFollowMap();
        tt.printFollowMapNew();


        System.out.println("table");
        System.out.println("");


        tt.initLL1table();
        tt.fillLL1table();

        tt.ll1table[0][0] = new Productions("Productions");


        tt.printTable();

        StringBuilder input = new StringBuilder();

        Stack<String> theStack = new Stack <String>();

        input.append("class id { } ; program { } ; ");
        input.append("$");
        theStack.push("$");
        theStack.push("prog");

        TreeNode currentNode = null;

        String [] inputArray = input.toString().split(" ");

        List <String> data = new ArrayList<String>();
        data.addAll(Arrays.asList(inputArray));

        boolean completed=false;
        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter("DerivationProof.txt"));
            out.printf("%70s %5s %70s ", "The used Rule: ","||", "Sentential Form");
            out.println();
            out.close();
        }catch(Exception e){
            System.out.println(e);
        }



        System.out.println("Begin !");

        tt.printStackAndData(data, theStack);

            while (!completed) {

                tt.printStackAndData(data, theStack);
                String top = theStack.pop();
                String[] inputNew = tt.findProd(top, data.get(0));
//                if(top.toString().equals("prog")){
//                    TreeNode startNode = new TreeNode(top);
//                    currentNode = startNode;
//                    System.out.println("currNode is: "+currentNode.getToken());
//                    tt.ast.setStartNode(startNode);
//                    tt.ast.getStartNode().setChildren(inputNew);
//                }else{
//                    currentNode = currentNode.getChildren().get(0);
//                    System.out.println("currNode is: "+currentNode.getToken());
//                }


                try {
                    out = new PrintWriter(new FileWriter("DerivationProof.txt",true));
                    out.printf("%70s %5s %70s ", top + " -> " + Arrays.toString(inputNew) ,"||", "");
                    out.println();
                    out.close();
                }catch(Exception e){
                    System.out.println(e);
                }
                for (int i = inputNew.length - 1; i >= 0; i--) {
                    if (!inputNew[i].equals("EPSILON")) {
                        theStack.push(inputNew[i].toString());
                    }
                }

                while (data.get(0).toString().equals(theStack.peek().toString())) {
                    String theTop = theStack.pop();
                    tt.successfullyParsed.add(theTop);
                    data = data.subList(1, data.size());
                    System.out.println();
                    System.out.println();
                    System.out.println("pop! \n");
                    tt.printStackAndData(data, theStack);
                    if (theStack.empty()) {
                        completed = true;
                        System.out.println("\nThe grammar is correct! Success");
                        System.exit(0);
                    }
                }

                if (theStack.peek().equals("Error")) {
                    try {
                        out = new PrintWriter(new FileWriter("Error.txt"));
                        out.println("\n Error. The following token is not allowed at this locations: " + data.get(0).toString());
                        out.print(Arrays.toString(tt.successfullyParsed.toArray()));
                        out.print(" {Error Here} " + Arrays.toString(data.toArray()));
                        out.close();
                        System.exit(0);
                    }catch(Exception e){
                        System.out.println(e);
                    }
                }
                System.out.println();
                System.out.println();
            }
    }

    public void printStackAndData(List<String> al, Stack<String> stack){

        PrintWriter out;
        try {
            out = new PrintWriter(new FileWriter("DerivationProof.txt", true));
            out.printf("%70s %5s %70s" ,"","||",Arrays.toString(successfullyParsed.toArray()));

            for (int i = stack.toArray().length - 1; i >= 0; i--) {
                out.print(" " + stack.toArray()[i].toString());
            }
            out.println();
            out.close();
        }catch(Exception e){
                System.out.println(e);
            }

    }

    public String [] findProd(String prod, String first){
        for(int i= 1; i< non_terminals.toArray().length+1;i++){
            if(ll1table[i][0].labelName.equals(prod)){
                for(int j= 1; j< terminals.toArray().length+1;j++){
                    if(ll1table[0][j].labelName.equals(first)){
                        if(ll1table[i][j].grammar==null){
                            return new String[]{"Error"};
                        }else{
                            return ll1table[i][j].grammar;
                        }
                    }
                }
            }
        }
        return null;
    }

}

