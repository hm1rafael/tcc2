/*
 * $Id: \\dds\\src\\Research\\ckjm.RCS\\src\\gr\\spinellis\\ckjm\\ClassMetrics.java,v 1.12 2007/07/25 12:24:00 dds Exp $
 *
 * (C) Copyright 2005 Diomidis Spinellis
 *
 * Permission to use, copy, and distribute this software and its
 * documentation for any purpose and without fee is hereby granted,
 * provided that the above copyright notice appear in all copies and that
 * both that copyright notice and this permission notice appear in
 * supporting documentation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND WITHOUT ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, WITHOUT LIMITATION, THE IMPLIED WARRANTIES OF
 * MERCHANTIBILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 */

package gr.spinellis.ckjm;

import java.util.HashSet;

/**
 * Store details needed for calculating a class's Chidamber-Kemerer metrics.
 * Most fields in this class are set by ClassVisitor.
 * This class also encapsulates some policy decision regarding metrics
 * measurement.
 *
 * @see ClassVisitor
 * @version $Revision: 1.12 $
 * @author <a href="http://www.spinellis.gr">Diomidis Spinellis</a>
 */
public class ClassMetrics {
    /** Weighted methods per class */
    private int wmc;
    /** Number of children */
    private int noc;
    /** Response for a Class */
    private int rfc;
    /** Coupling between object classes */
    private int cbo;
    /** Depth of inheritence tree */
    private int dit;
    /** Lack of cohesion in methods */
    private int lcom;
    /** Number of public methods */
    private int npm;
    /** True if the class has been visited by the metrics gatherer */
    private boolean visited;
    /** True if the class is public */
    private boolean isPublicClass;
    /** Coupled classes: classes that use this class */
    private final HashSet<String> afferentCoupledClasses;

    /** Default constructor. */
    ClassMetrics() {
	this.wmc = 0;
	this.noc = 0;
	this.cbo = 0;
	this.npm = 0;
	this.visited = false;
	this.afferentCoupledClasses = new HashSet<String>();
    }

    /** Increment the weighted methods count */
    public void incWmc() { this.wmc++; }
    /** Return the weighted methods per class metric */
    public int getWmc() { return this.wmc; }

    public void setWmc(int wmc) {
	this.wmc = wmc;
    }

    /** Increment the number of children */
    public void incNoc() { this.noc++; }
    /** Return the number of children */
    public int getNoc() { return this.noc; }

    public void setNoc(int noc) {
	this.noc = noc;
    }

    /** Increment the Response for a Class */
    public void setRfc(int r) { this.rfc = r; }
    /** Return the Response for a Class */
    public int getRfc() { return this.rfc; }

    /** Set the depth of inheritence tree metric */
    public void setDit(int d) { this.dit = d; }
    /** Return the depth of the class's inheritance tree */
    public int getDit() { return this.dit; }

    /** Set the coupling between object classes metric */
    public void setCbo(int c) { this.cbo = c; }
    /** Return the coupling between object classes metric */
    public int getCbo() { return this.cbo; }

    /** Return the class's lack of cohesion in methods metric */
    public int getLcom() { return this.lcom; }
    /** Set the class's lack of cohesion in methods metric */
    public void setLcom(int l) { this.lcom = l; }

    /** Return the class's afferent couplings metric */
    public int getCa() { return this.afferentCoupledClasses.size(); }
    /** Add a class to the set of classes that depend on this class */
    public void addAfferentCoupling(String name) { this.afferentCoupledClasses.add(name); }

    /** Increment the number of public methods count */
    public void incNpm() { this.npm++; }
    /** Return the number of public methods metric */
    public int getNpm() { return this.npm; }

    public void setNpm(int npm) {
	this.npm = npm;
    }

    /** Return true if the class is public */
    public boolean isPublic() { return this.isPublicClass; }
    /** Call to set the class as public */
    public void setPublic() { this.isPublicClass = true; }

    /** Return true if the class name is part of the Java SDK */
    public static boolean isJdkClass(String s) {
	return (s.startsWith("java.") ||
		s.startsWith("javax.") ||
		s.startsWith("org.omg.") ||
		s.startsWith("org.w3c.dom.") ||
		s.startsWith("org.xml.sax."));
    }

    /** Return the 6 CK metrics plus Ce as a space-separated string */
    @Override
    public String toString() {
	return (
		this.wmc +
		" " + getDit() +
		" " + this.noc +
		" " + this.cbo +
		" " + this.rfc +
		" " + this.lcom +
		" " + getCa()+
		" " + this.npm);
    }

    /** Mark the instance as visited by the metrics analyzer */
    public void setVisited() { this.visited = true; }
    /**
     * Return true if the class has been visited by the metrics analyzer.
     * Classes may appear in the collection as a result of some kind
     * of coupling.  However, unless they are visited and analyzed,
     * we do not want them to appear in the output results.
     */
    public boolean isVisited() { return this.visited; }
}
