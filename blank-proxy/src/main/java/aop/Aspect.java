package aop;

public class Aspect {

    //切点表达式
    private PointCut pointCut;
    //增强通知
    private Advice advice;

    public Aspect(PointCut pointCut, Advice advice) {
        super();
        this.pointCut = pointCut;
        this.advice = advice;
    }

    public PointCut getPointCut() {
        return pointCut;
    }

    public void setPointCut(PointCut pointCut) {
        this.pointCut = pointCut;
    }

    public Advice getAdvice() {
        return advice;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }




}
