/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.dom.huesped;

import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.eventbus.PropertyDomainEvent;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.apache.isis.applib.util.ObjectContracts;

@javax.jdo.annotations.PersistenceCapable(
        identityType=IdentityType.DATASTORE,
        schema = "simple",
        table = "Huesped"
)
@javax.jdo.annotations.DatastoreIdentity(
        strategy=javax.jdo.annotations.IdGeneratorStrategy.IDENTITY,
         column="id")
@javax.jdo.annotations.Version(
//        strategy=VersionStrategy.VERSION_NUMBER,
        strategy= VersionStrategy.DATE_TIME,
        column="version")
@javax.jdo.annotations.Queries({
        @javax.jdo.annotations.Query(
                name = "find", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Huesped "),
        @javax.jdo.annotations.Query(
                name = "findByName", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Huesped "
                        + "WHERE name.indexOf(:name) >= 0 "),
        @javax.jdo.annotations.Query(
            	
                name = "findByTitular", language = "JDOQL",
                value = "SELECT "
                        + "FROM domainapp.dom.simple.Huesped "
                        + "WHERE titularRes.toString(:titularRes) == 'TITULAR' ")
        }
		
)


@javax.jdo.annotations.Unique(name="Huesped_name_UNQ", members = {"name"})
@DomainObject
public class Huesped implements Comparable<Huesped> {

    public static final int NAME_LENGTH = 40;


    public TranslatableString title() {
        return TranslatableString.tr("Huésped: {name}", "name", getName());
    }


    public static class NameDomainEvent extends PropertyDomainEvent<Huesped,String> {}
    @javax.jdo.annotations.Column(
            allowsNull="false",
            length = NAME_LENGTH
    )
    @Property(
        domainEvent = NameDomainEvent.class
    )
    private String name;
    public String getName() {
        return name;
    }
    public void setName(final String name) {
        this.name = name;
    }

    
    
    private String numTel;
    @javax.jdo.annotations.Column(allowsNull="true")
    public String getNumTel() {
        return numTel;
    }
    @javax.jdo.annotations.Column(allowsNull="true")
    public void setNumTel(final String numTel) {
        this.numTel = numTel;
    }
    
    private String email;
    @javax.jdo.annotations.Column(allowsNull="true")
    public String getEmail() {
        return email;
    }
    @javax.jdo.annotations.Column(allowsNull="true")
    public void setEmail(final String email) {
        this.email = email;
    }
    
    private String domicilio;
    @javax.jdo.annotations.Column(allowsNull="true")
    public String getDomicilio() {
        return domicilio;
    }
    @javax.jdo.annotations.Column(allowsNull="true")
    public void setDomicilio(final String domicilio) {
        this.domicilio = domicilio;
    }
    
    
    private ListaPais pais;
    
    
    @javax.jdo.annotations.Column(allowsNull="true")
    public ListaPais getPais() { 
    	return pais; 
    }
    public void setPais(final ListaPais pais) {
    	this.pais = pais;
    }
    

    
    private E_titular titularRes;
    @javax.jdo.annotations.Column(allowsNull="false")
    public E_titular getTitularRes() {
    	return titularRes; 
    }
    public void setTitularRes(E_titular titularRes) {
    	this.titularRes = titularRes;
    }
    
    private E_canalVenta canalVenta;
    @javax.jdo.annotations.Column(allowsNull="false")
    public E_canalVenta getCanalVenta() {
    	return canalVenta; 
    }
    public void setCanalVenta(E_canalVenta canalVenta) {
    	this.canalVenta = canalVenta;
    }
    

    public TranslatableString validateName(final String name) {
        return name != null && name.contains("!")? TranslatableString.tr("El signo de exclamación no está permitido"): null;
    }

    public static class DeleteDomainEvent extends ActionDomainEvent<Huesped> {}
    @Action(
            domainEvent = DeleteDomainEvent.class,
            semantics = SemanticsOf.NON_IDEMPOTENT_ARE_YOU_SURE
    )
    public void delete() {
        repositoryService.remove(this);
    }



    
    public int compareTo(final Huesped other) {
        return ObjectContracts.compare(this, other, "name");
    }
    

    
    public enum E_titular{
    	TITULAR, NOTITULAR;
    }
    
    public enum E_canalVenta{
    	Booking, Despegar;
    }

    @javax.inject.Inject
    RepositoryService repositoryService;

}
