package aop;

public class PointCut {

    /**
     * 让用户来定义它的切入点
     * 正则，哪些包，哪些方法，哪些类
     */
    //类名匹配模式
    private String classPattern;

    //方法匹配模式
    private String methodPattern;

    public PointCut(String classPattern, String methodPattern) {
        super();
        this.classPattern = classPattern;
        this.methodPattern = methodPattern;
    }

    public String getClassPattern() {
        return classPattern;
    }

    public void setClassPattern(String classPattern) {
        this.classPattern = classPattern;
    }

    public String getMethodPattern() {
        return methodPattern;
    }

    public void setMethodPattern(String methodPattern) {
        this.methodPattern = methodPattern;
    }
}
