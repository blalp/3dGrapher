package com.blalp.dgraph;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.glu.GLU;
import javafx.scene.input.ZoomEvent;
import org.codehaus.commons.compiler.CompileException;
import org.codehaus.janino.ExpressionEvaluator;
import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.controllers.ControllerType;
import org.jzy3d.chart.controllers.camera.AbstractCameraController;
import org.jzy3d.chart.controllers.mouse.camera.AWTCameraMouseController;
import org.jzy3d.chart.controllers.thread.camera.CameraThreadController;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.colors.Color;
import org.jzy3d.events.ControllerEvent;
import org.jzy3d.events.ControllerEventListener;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.Scatter;
import org.jzy3d.plot3d.rendering.canvas.Quality;
import org.jzy3d.plot3d.rendering.view.Camera;
import org.jzy3d.plot3d.rendering.view.modes.ViewPositionMode;
import org.jzy3d.plot3d.transform.Transform;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class Parametric extends AbstractAnalysis{
    private static String[] functions= new String[]{"0","0","0"};
    private static Range range = new Range(0,500000);
    private static float step = 1;
    public static void main(String[] functionsInput,Range range1,float step1) throws Exception {
        functions=functionsInput;
        range=range1;
        step=step1;
        AnalysisLauncher.open(new Parametric());
    }
    @Override
    public void init(){
        double x;
        double y;
        double z;
        float xx;
        float yy;
        float zz;
        float a;
        Coord3d[] points = new Coord3d[(int)Math.ceil(range.getMax()*(1/step))-(int)Math.floor(range.getMin())];
        Color[] colors = new Color[(int)Math.ceil(range.getMax()*(1/step))-(int)Math.floor(range.getMin())];

        //Random r = new Random();
        //r.setSeed(0);
        ExpressionEvaluator ex = new ExpressionEvaluator();
        ExpressionEvaluator ey = new ExpressionEvaluator();
        ExpressionEvaluator ez = new ExpressionEvaluator();
        // The expression will have two "int" parameters: "a" and "b".
        ex.setParameters(new String[] {"t"}, new Class[] {float.class});
        ey.setParameters(new String[] {"t"}, new Class[] {float.class});
        ez.setParameters(new String[] {"t"}, new Class[] {float.class});

        // And the expression (i.e. "result") type is also "int".
        ex.setExpressionType(double.class);
        ey.setExpressionType(double.class);
        ez.setExpressionType(double.class);

        // And now we "cook" (scan, parse, compile and load) the fabulous expression.
        try {//(cos(t)){t<100000}+(sin(t)){t>100000}
            System.out.println(functions[0]);
            ex.cook(functions[0]);
            ey.cook(functions[1]);
            ez.cook(functions[2]);
        } catch (CompileException el){
            el.printStackTrace();
            return;
        }
        // Eventually we evaluate the expression - and that goes super-fast.
        for(int i=(int)Math.floor(range.getMin());i<(int)Math.ceil(range.getMax()*(1/step));i++){
            try {
                x=(double) ex.evaluate(new Object[] { (i*step) });
                y=(double) ey.evaluate(new Object[] { (i*step) });
                z=(double) ez.evaluate(new Object[] { (i*step) });
                points[i-(int)Math.floor(range.getMin())]=new Coord3d(x,y,z);
                a = 0.25f;
                xx=(float)x;
                yy=(float)y;
                zz=(float)z;
                colors[i-(int)Math.floor(range.getMin())]=new Color(xx,yy,zz,a);//new Color(xx,yy,zz,a);
            } catch (Exception e){
                e.printStackTrace();
            }
            //y=(float)(Math.cos(i)/10*Math.sin(i));
            //z=(float)i;
            //x=r.nextFloat()-0.5f;
            //y=r.nextFloat()-0.5f;
            //z=r.nextFloat()-0.5f;
        }
        Scatter scatter = new Scatter(points,colors);
        chart = AWTChartComponentFactory.chart(Quality.Advanced,"newt");
        chart.getScene().add(scatter);
        chart.setViewMode(ViewPositionMode.FREE);
    }
}
