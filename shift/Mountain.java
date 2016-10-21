/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shift;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Area;

/**
 *
 * @author phusisian
 */
public class Mountain 
{
    private double relX;
    private Point[] mountainPoints;
    private int height;
    private double x, y;
    private double dSpin;
    private double spin = 0;
    private double spinRadius;
    private int sortDistance;
    private int[] xPoints, yPoints;
    private Polygon mountainPolygon;
    private Point topPoint;
    public Mountain(double xIn, double yIn, int mountainType, int sortDistanceIn)
    {
        x = xIn;
        relX = (x-WorldPanel.worldX);
        y = yIn;
        dSpin = Math.toRadians(1.5+Math.random()*2.0);
        fillMountainPoints(mountainType);
        sortDistance = sortDistanceIn;
        spinRadius = 15 + (10*Math.random());
        setMountainPolygon();
    }
    
    private void fillMountainPoints(int mountainType)
    {
        switch(mountainType)
        {//switched from using points to int x and y vals
            case 1:
                mountainPoints = new Point[3];
                mountainPoints[0] = new Point(0,479);
                mountainPoints[1] = new Point(251,0);
                topPoint = mountainPoints[1];
                mountainPoints[2] = new Point(513, 479);
                height = 479;
                break;
            case 2:
                mountainPoints = new Point[3];
                mountainPoints[0] = new Point(0,545);
                mountainPoints[1] = new Point(245,0);
                topPoint = mountainPoints[1];
                mountainPoints[2] = new Point(507,545);
                height = 545;
                break;
            case 3:
                mountainPoints = new Point[5];
                mountainPoints[0] = new Point(0,383);
                mountainPoints[1] = new Point(107,115);
                mountainPoints[2] = new Point(169,221);
                mountainPoints[3] = new Point(257,0);
                topPoint = mountainPoints[3];
                mountainPoints[4] = new Point(472,383);
                height = 383;
                break;
            case 4:
                mountainPoints = new Point[5];
                mountainPoints[0] = new Point(0,440);
                mountainPoints[1] = new Point(220,0);
                topPoint = mountainPoints[1];
                mountainPoints[2] = new Point(364,285);
                mountainPoints[3] = new Point(401,220);
                mountainPoints[4] = new Point(532,440);
                height = 440;
                break;
            case 5:
                mountainPoints = new Point[5];
                mountainPoints[0] = new Point(0,525);
                mountainPoints[1] = new Point(238,0);
                topPoint = mountainPoints[1];
                mountainPoints[2] = new Point(403,349);
                mountainPoints[3] = new Point(473,251);
                mountainPoints[4] = new Point(594,525);
                height = 525;
                break;
            case 6:
                mountainPoints = new Point[3];
                mountainPoints[0] = new Point(0,470);
                mountainPoints[1] = new Point(268,0);
                topPoint = mountainPoints[1];
                mountainPoints[2] = new Point(511,470);
                height = 470;
                break;
            case 7:
                mountainPoints = new Point[3];
                mountainPoints[0] = new Point(0,383);
                mountainPoints[1] = new Point(192,0);
                topPoint = mountainPoints[1];
                mountainPoints[2] = new Point(387,383);
                height = 383;
                break;
        }
    }
    
    private void setMountainPolygon()
    {
        y=WorldPanel.worldY;
        xPoints = new int[mountainPoints.length];
        yPoints = new int[mountainPoints.length];
        int centerX = (int)(mountainPoints[mountainPoints.length-1].getX()/2);
        for(int i = 0; i < mountainPoints.length; i++)
        {
            xPoints[i] = (int)(WorldPanel.worldX + WorldPanel.scale*relX + (mountainPoints[i].getX()*WorldPanel.scale));
            //xPoints[i] = (int)(WorldPanel.worldX + WorldPanel.scale*(x-WorldPanel.worldX) + ((mountainPoints[i].getX()*WorldPanel.scale)));
            //xPoints[i]=(int)(WorldPanel.worldX+WorldPanel.scale*(x-WorldPanel.worldX)+(mountainPoints[i].getX()*WorldPanel.scale));
            yPoints[i]=(int)(WorldPanel.worldY-(height*WorldPanel.scale)+(mountainPoints[i].getY()*WorldPanel.scale) - (WorldPanel.scale*Math.sin(spin)*spinRadius));
        }
        mountainPolygon = new Polygon(xPoints, yPoints, xPoints.length);
        
    }
    
    /*private Polygon resizePolygon(int xPoints[], int yPoints[], double x, double y, double scale)
    {
        //Polygon poly = (Polygon)p.clone();
        int xPoints2[] = new int[xPoints.length];
        int yPoints2[] = new int[yPoints.length];
        
        for(int i = 0; i < xPoints.length; i++)
        {
            xPoints2[i]= (int)(xPoints[i]-(x/WorldPanel.scale));
            yPoints2[i] = (int)(yPoints[i]-(y/WorldPanel.scale));
        }
        int centerX = (int)((double)(xPoints[xPoints.length-1])/2.0);
        int height = (int)(new Polygon(xPoints, yPoints, xPoints.length).getBoundingBox().getHeight()*WorldPanel.scale);
        System.out.println(centerX);
        for(int i = 0; i < xPoints.length; i++)
        {
            int dx = xPoints[i]-centerX;
            
            xPoints2[i] = (int)( centerX + scale*(dx));
            yPoints2[i] = (int)(y-height + scale*yPoints[i]);
        }
        return new Polygon(xPoints2, yPoints2, xPoints2.length);
    }*/
    
    private Polygon getScaledPolygon(double scale)
    {
        //y=WorldPanel.worldY;
        int[] xPoints2 = new int[mountainPoints.length];
        int[] yPoints2 = new int[mountainPoints.length];
        double normalWidth = (topPoint.getX()*WorldPanel.scale);
        int centerX = (int)(mountainPoints[mountainPoints.length-1].getX()/2);
        for(int i = 0; i < mountainPoints.length; i++)
        {
            double amountShift = ((normalWidth*scale)-normalWidth);
            xPoints2[i] = (int)(WorldPanel.worldX + WorldPanel.scale*relX + (mountainPoints[i].getX()*WorldPanel.scale*scale)-amountShift);
            //xPoints[i] = (int)(WorldPanel.worldX + WorldPanel.scale*(x-WorldPanel.worldX) + ((mountainPoints[i].getX()*WorldPanel.scale)));
            //xPoints[i]=(int)(WorldPanel.worldX+WorldPanel.scale*(x-WorldPanel.worldX)+(mountainPoints[i].getX()*WorldPanel.scale));
            yPoints2[i]=(int)(WorldPanel.worldY-(height*WorldPanel.scale*scale)+(mountainPoints[i].getY()*WorldPanel.scale*scale) - (WorldPanel.scale*Math.sin(spin)*spinRadius));
        }
        return new Polygon(xPoints2, yPoints2, xPoints2.length);
        
    }
    
    public void draw(Graphics g, Area a)
    {
        Graphics2D g2 = (Graphics2D)g;
        
        Composite originalComposite = g2.getComposite();
        /*y=WorldPanel.worldY;
        int[] xPoints = new int[mountainPoints.length];
        int[] yPoints = new int[mountainPoints.length];
        for(int i = 0; i < mountainPoints.length; i++)
        {
            
            //mountainPoints[i] = new Point((int)(mountainPoints[i].getX()*WorldPanel.scale), (int)(mountainPoints[i].getY()*WorldPanel.scale));
            xPoints[i]=(int)(WorldPanel.worldX+WorldPanel.scale*(x-WorldPanel.worldX)+(mountainPoints[i].getX()*WorldPanel.scale));
            yPoints[i]=(int)(WorldPanel.worldY-(height*WorldPanel.scale)+(mountainPoints[i].getY()*WorldPanel.scale) - (WorldPanel.scale*Math.sin(spin)*spinRadius));
        }*/
        setMountainPolygon();
        spin += dSpin;
        if(spin > 2*Math.PI)
        {
            spin -= 2*Math.PI;
        }
        
        //g.setColor(Color.RED);
        //g.drawPolygon(getScaledPolygon(2));
        
        int upperAlpha = 120;
        int lowerAlpha = 20;
        int numShades = 10;
        
        
        for(int i = 1; i < numShades+1; i++)
        {
            Area aCopy = (Area)a.clone();
            aCopy.intersect(new Area(getScaledPolygon(1+.1*((double)i/(double)numShades))));
            AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)(((((upperAlpha) - (lowerAlpha)))/numShades)/255.0));
            g2.setComposite(ac);
            
            //g2.setStroke(new BasicStroke((int)((double)i*5), BasicStroke.CAP_SQUARE,  BasicStroke.JOIN_MITER));
            //g.setColor(Color.BLACK);
            //Area newArea = new Area();
            //newArea.intersect(aCopy);
            //g2.fill(newArea);
            //2.fill(new Area(getScaledPolygon(1+.2*((double)i/(double)numShades))).intersect(aCopy));
            //g.fillPolygon(getScaledPolygon(1+.2*((double)i/(double)numShades)));
            
            //Area shadeArea = new Area(resizePolygon(xPoints, yPoints, x, y,  1+(i*0.05)));
            //g2.fill(shadeArea);
            //shadeArea.intersect(a);
            //g.setColor(new Color(0,0,0,lowerAlpha+((upperAlpha-lowerAlpha)/numShades)));
            //g2.fill(shadeArea);
             g.setColor(new Color(65, 0, 120));
             //g.setColor(Color.BLACK);
            g2.fill(aCopy);
        }
        g2.setComposite(originalComposite);
        int grayInc = 5;
        g.setColor(new Color(Color.GRAY.getRed() - grayInc * sortDistance, Color.GRAY.getGreen() - grayInc * sortDistance, Color.GRAY.getBlue() - grayInc * sortDistance));
        g.fillPolygon(mountainPolygon);
        
        g2.setStroke(new BasicStroke(1));
       
        
        //g.setColor(Color.BLUE);
        //g2.fill(aCopy);
        //g2.fill(a);
        /*g.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        g.drawPolygon(xPoints, yPoints, xPoints.length);*/
    }
    
    public Polygon getMountainPolygon()
    {
        return mountainPolygon;
    }
}
