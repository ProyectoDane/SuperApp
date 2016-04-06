package ar.uba.fi.superapp.utils;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.superapp.models.Category;
import ar.uba.fi.superapp.models.SuperImage;

public class PresetsHelper {

	public static void loadCategoriesAndImages(){
		ArrayList<Category> mCategories = new ArrayList<Category>();
		ArrayList<SuperImage> images = new ArrayList<SuperImage>();
		images.add(new SuperImage("Alfajor","gfx/items/alfajor.jpg",true));
		images.add(new SuperImage("Aceite","gfx/items/aceite.jpg",true));
		images.add(new SuperImage("Arroz","gfx/items/arroz.jpg",true));
		images.add(new SuperImage("Atun","gfx/items/atun.jpg",true));
		images.add(new SuperImage("Azucar","gfx/items/azucar.jpg",true));
		images.add(new SuperImage("Cafe","gfx/items/cafe.jpg",true));
		images.add(new SuperImage("Cereal","gfx/items/cereal.jpg",true));
		images.add(new SuperImage("Coca","gfx/items/coca.jpg",true));
		images.add(new SuperImage("Fideos","gfx/items/fideos.jpg",true));
		images.add(new SuperImage("Helado","gfx/items/helado.jpg",true));
		images.add(new SuperImage("Leche","gfx/items/leche.jpg",true));
		images.add(new SuperImage("Pan","gfx/items/pan.jpg",true));
		images.add(new SuperImage("Pate","gfx/items/pate.jpg",true));
		images.add(new SuperImage("Pizza","gfx/items/pizza.jpg",true));
		images.add(new SuperImage("Pure","gfx/items/pure.jpg",true));
		images.add(new SuperImage("Sal","gfx/items/sal.jpg",true));
		images.add(new SuperImage("Te","gfx/items/te.jpg",true));
		images.add(new SuperImage("Yerba","gfx/items/yerba.jpg",true));
		mCategories.add(new Category("Almacen","gfx/items/banana.jpg",images));
		images = new ArrayList<SuperImage>();
		images.add(new SuperImage("Ajo","gfx/items/ajo.jpg",true));
		images.add(new SuperImage("Anana","gfx/items/anana.jpg",true));
		images.add(new SuperImage("Arveja","gfx/items/arveja.jpg",true));
		images.add(new SuperImage("Banana","gfx/items/banana.jpg",true));
		images.add(new SuperImage("Batata","gfx/items/batata.jpg",true));
		images.add(new SuperImage("Berenjena","gfx/items/berenjena.jpg",true));
		images.add(new SuperImage("Brocoli","gfx/items/brocoli.jpg",true));
		images.add(new SuperImage("Cebolla","gfx/items/cebolla.jpg",true));
		images.add(new SuperImage("Cereza","gfx/items/cereza.jpg",true));
		images.add(new SuperImage("Choclo","gfx/items/choclo.jpg",true));
		images.add(new SuperImage("Durazno","gfx/items/durazno.jpg",true));
		images.add(new SuperImage("Hongos","gfx/items/hongos.jpg",true));
		images.add(new SuperImage("Kiwi","gfx/items/kiwi.jpg",true));
		images.add(new SuperImage("Lechuga","gfx/items/lechuga.jpg",true));
		images.add(new SuperImage("Limon","gfx/items/limon.jpg",true));
		images.add(new SuperImage("Manzana","gfx/items/manzana.jpg",true));
		images.add(new SuperImage("Melon","gfx/items/melon.jpg",true));
		images.add(new SuperImage("Morron","gfx/items/morron.jpg",true));
		images.add(new SuperImage("Naranja","gfx/items/naranja.jpg",true));
		images.add(new SuperImage("Papa","gfx/items/papa.jpg",true));
		images.add(new SuperImage("Palta","gfx/items/palta.jpg",true));
		images.add(new SuperImage("Pepino","gfx/items/pepino.jpg",true));
		images.add(new SuperImage("Pera","gfx/items/pera.jpg",true));
		images.add(new SuperImage("Sandia","gfx/items/sandia.jpg",true));
		images.add(new SuperImage("Tomate","gfx/items/tomate.jpg",true));
		images.add(new SuperImage("Uva","gfx/items/uva.jpg",true));
		images.add(new SuperImage("Zapallo","gfx/items/zapallo.jpg",true));
		mCategories.add(new Category("Verduleria","gfx/items/lata.jpg",images));
		
		images = new ArrayList<SuperImage>();
		images.add(new SuperImage("Carne","gfx/items/carne.jpg",true));
		images.add(new SuperImage("Huevo","gfx/items/huevo.jpg",true));
		images.add(new SuperImage("Jamon","gfx/items/jamon.jpg",true));
		images.add(new SuperImage("Pescado","gfx/items/pescado.jpg",true));
		images.add(new SuperImage("Pollo","gfx/items/pollo.jpg",true));
		images.add(new SuperImage("Queso","gfx/items/queso.jpg",true));
		mCategories.add(new Category("Frescos","gfx/items/jabon.jpg",images));
		
		images = new ArrayList<SuperImage>();
		images.add(new SuperImage("Balde","gfx/items/balde.jpg",true));
		images.add(new SuperImage("Escoba","gfx/items/escoba.jpg",true));
		images.add(new SuperImage("Esponja","gfx/items/esponja.jpg",true));
		images.add(new SuperImage("Lata","gfx/items/lata.jpg",true));
		images.add(new SuperImage("Jabon","gfx/items/jabon.jpg",true));
		images.add(new SuperImage("Lapiz","gfx/items/lapiz.jpg",true));
		images.add(new SuperImage("Mate","gfx/items/mate.jpg",true));
		images.add(new SuperImage("Pala","gfx/items/pala.jpg",true));
		images.add(new SuperImage("Sopa","gfx/items/sopa.jpg",true));
		images.add(new SuperImage("Tostada","gfx/items/tostada.jpg",true));
		images.add(new SuperImage("Vaso","gfx/items/vaso.jpg",true));
		mCategories.add(new Category("Otros","gfx/items/jabon.jpg",images));
		List<Category> categories = Category.find(Category.class, null);
		if(categories.size() == 0){
			for (Category category : mCategories) {
				category.save();
			}
		}
		categories = Category.find(Category.class, null);
	}

}
