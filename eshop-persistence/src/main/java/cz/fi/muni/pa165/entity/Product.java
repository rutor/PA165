package cz.fi.muni.pa165.entity;

import cz.fi.muni.pa165.enums.Color;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

@Entity
@Table(name="ESHOP_PRODUCTS")
public class Product {
		
        @Id
        @GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
        
        // nonullable unique
        @Column(nullable = false, unique = true)
        private String name;
        
        @Enumerated(EnumType.STRING)
        private Color color;
        
        @Temporal(TemporalType.DATE)
        private Date addedDate;
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public void setColor(Color color) {
            this.color = color;
        }
        
        public void setDate(Date date) {
            this.addedDate = date;
        }
        
        public Long getId(){
            return id;
        }
        
        public String getName() {
            return name;
        }
        
        public Color getColor() {
            return color;
        }
        
        public Date getAddedDate() {
            return addedDate;
        }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof Product) {
            Product other = (Product) obj;
            if (!Objects.equals(this.name, other.getName())) return false;
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 31;
        
        hash *= Objects.hashCode(name);
        
        return hash;
    }
        
        
}
