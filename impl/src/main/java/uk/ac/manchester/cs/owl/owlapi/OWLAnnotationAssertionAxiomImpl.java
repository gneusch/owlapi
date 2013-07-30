/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.manchester.cs.owl.owlapi;

import java.util.Collection;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationSubject;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;

/** Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Nov-2006<br>
 * <br> */
@SuppressWarnings("javadoc")
public class OWLAnnotationAssertionAxiomImpl extends OWLAxiomImpl implements
        OWLAnnotationAssertionAxiom {
    private static final long serialVersionUID = 40000L;
    private final OWLAnnotationSubject subject;
    private final OWLAnnotationProperty property;
    private final OWLAnnotationValue value;

    public OWLAnnotationAssertionAxiomImpl(OWLAnnotationSubject subject,
            OWLAnnotationProperty property, OWLAnnotationValue value,
            Collection<? extends OWLAnnotation> annotations) {
        super(annotations);
        this.subject = subject;
        this.property = property;
        this.value = value;
    }

    @Override
    public OWLAnnotationAssertionAxiom getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return df.getOWLAnnotationAssertionAxiom(getProperty(), getSubject(), getValue());
    }

    /** Determines if this annotation assertion deprecates the IRI that is the
     * subject of the annotation.
     * 
     * @return <code>true</code> if this annotation assertion deprecates the
     *         subject IRI of the assertion, otherwise <code>false</code>.
     * @see{@link 
     *            org.semanticweb.owlapi.model.OWLAnnotation#isDeprecatedIRIAnnotation
     *            ()} */
    @Override
    public boolean isDeprecatedIRIAssertion() {
        return property.isDeprecated() && getAnnotation().isDeprecatedIRIAnnotation();
    }

    @Override
    public OWLAnnotationAssertionAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return df.getOWLAnnotationAssertionAxiom(getProperty(), getSubject(), getValue(),
                mergeAnnos(annotations));
    }

    @Override
    public OWLAnnotationValue getValue() {
        return value;
    }

    @Override
    public OWLAnnotationSubject getSubject() {
        return subject;
    }

    @Override
    public OWLAnnotationProperty getProperty() {
        return property;
    }

    @Override
    public OWLAnnotation getAnnotation() {
        return df.getOWLAnnotation(property, value);
    }

    @Override
    public boolean isLogicalAxiom() {
        return false;
    }

    @Override
    public boolean isAnnotationAxiom() {
        return true;
    }

    @Override
    protected int compareObjectOfSameType(OWLObject object) {
        OWLAnnotationAssertionAxiom other = (OWLAnnotationAssertionAxiom) object;
        int diff = 0;
        diff = subject.compareTo(other.getSubject());
        if (diff != 0) {
            return diff;
        }
        diff = property.compareTo(other.getProperty());
        if (diff != 0) {
            return diff;
        }
        return value.compareTo(other.getValue());
    }

    @Override
    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    @Override
    public AxiomType<?> getAxiomType() {
        return AxiomType.ANNOTATION_ASSERTION;
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            // superclass is responsible for null, identity, owlaxiom type and
            // annotations
            if (!(obj instanceof OWLAnnotationAssertionAxiom)) {
                return false;
            }
            OWLAnnotationAssertionAxiom other = (OWLAnnotationAssertionAxiom) obj;
            return subject.equals(other.getSubject())
                    && property.equals(other.getProperty())
                    && value.equals(other.getValue());
        }
        return false;
    }
}