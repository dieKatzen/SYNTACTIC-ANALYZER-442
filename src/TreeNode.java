import java.util.ArrayList;

public class TreeNode{
    String token;
    String lex;
    Boolean isEnd=false;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLex() {
        return lex;
    }

    public void setLex(String lex) {
        this.lex = lex;
    }

    public TreeNode getSibling() {
        return sibling;
    }

    public void setSibling(TreeNode sibling) {
        this.sibling = sibling;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public ArrayList<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<TreeNode> children) {
        this.children = children;
    }

    public void setChildren(String [] sarr) {


        for(int i = 0; i<sarr.length;i++){
            if(i==sarr.length-1) {
                children.add(new TreeNode(sarr[i],true,this));
            }else{
                children.add(new TreeNode(sarr[i]));
            }
        }
    }

    TreeNode sibling;

    public TreeNode(String token) {
        this.token = token;
    }

    public TreeNode(String token, Boolean isEnd) {
        this.isEnd = isEnd;
        this.token = token;
    }

    public TreeNode(String token, Boolean isEnd,TreeNode parent) {
        setParent(parent);
        this.isEnd = isEnd;
        this.token = token;
    }

    TreeNode parent;
    ArrayList<TreeNode> children;

}