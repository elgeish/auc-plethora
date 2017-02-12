/*
 * License Notice
 *
 * This file is part of Plethora.
 *
 * Plethora is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; version 2 of the License.
 *
 * Plethora is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Plethora; if not, write to the Free Software
 *
 * The original code is Plethora. The initial developer of the original
 * code is the Plethora Group at The American University in Cairo.
 *
 * http://www.cs.aucegypt.edu/
 *
 * ComplexParameter.java
 * Created on December 3, 2006
 */

package org.netbeans.modules.plethora.model;

import java.util.Vector;

/**
 * Description for a complex parameter
 *
 * @see Lg3dDataDocument
 *
 * @author Ismail El-Helw
 * @version 1.00
 */
public class ComplexParameter extends Parameter {
    
    private Vector<Parameter> params;
    
    public ComplexParameter(String type) {
        this(type, new Vector<Parameter>(3));
    }
    
    public ComplexParameter(String type, Vector<Parameter> params) {
        super(type);
        this.params = params;
    }

    public Vector<Parameter> getParameters() {
        return params;
    }

    public void setParameters(Vector<Parameter> params) {
        this.params = params;
    }

}