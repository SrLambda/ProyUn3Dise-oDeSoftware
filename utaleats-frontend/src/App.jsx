import React, { useState, useEffect } from "react";
import axios from 'axios';
import './App.css';

function App() {
    const [stores, setStores] = useState([]);
    const [cartOpen, setCartOpen] = useState(false);
    const [selectedStore, setSelectedStore] = useState(null);
    const [storeProducts, setStoreProducts] = useState([]);

    const openStoreView = (store) => {
        setSelectedStore(store);
        setStoreProducts(store.products || []); // o puedes hacer un fetch si los productos no vienen en la tienda
    };

    const recommendations = [
        { id: 1, title: "Pizza", image: "https://via.placeholder.com/120?text=Pizza" },
        { id: 2, title: "Sushi", image: "https://via.placeholder.com/120?text=Sushi" },
        { id: 3, title: "Hamburguesa", image: "https://via.placeholder.com/120?text=Hamburguesa" },
        { id: 4, title: "Ensaladas", image: "https://via.placeholder.com/120?text=Ensaladas" },
        { id: 5, title: "Postres", image: "https://via.placeholder.com/120?text=Postres" },
    ];

    useEffect(() => {
        axios.get("http://localhost:8080/store")
            .then((response) => {
                setStores(response.data);
            })
            .catch((error) => {
                console.error("Error al obtener tiendas:", error);
            });
    }, []);

    return (
        <div className="app-container">
            <header>
                <img src="/utal_eats_logo.png" alt="Utal Eats Logo" className="logo" />
            </header>

            <section className="recommendations">
                <h2>Recomendaciones para ti</h2>
                <div className="recommendations-list">
                    {recommendations.map((rec) => (
                        <div key={rec.id} className="rec-item">
                            <img src={rec.image} alt={rec.title} />
                            <p>{rec.title}</p>
                        </div>
                    ))}
                </div>
            </section>

            {/* Botón fijo flotante del carrito */}
            <div className="cart-button" onClick={() => setCartOpen(!cartOpen)}>
                <img src="/carrito.png" alt="Carrito" />
            </div>

            {cartOpen && <div className="backdrop" onClick={() => setCartOpen(false)}></div>}
            {/* Interfaz emergente del carrito */}
            <div className={`cart-overlay ${cartOpen ? 'open' : ''}`}>
                <div className="cart-header">
                    <button className="close-cart-button" onClick={() => setCartOpen(false)}>←</button>
                    <h2>Carrito</h2>
                </div>
                {/* agregar productos */}
                <button className="buy-button">Comprar</button>
            </div>

            {selectedStore && (
                <div className="store-overlay">
                    <div className="store-header">
                        <button className="close-store-button" onClick={() => setSelectedStore(null)}>←</button>
                        <h2>{selectedStore.name}</h2>
                    </div>
                    <div className="product-list">
                        {storeProducts.map((product, idx) => (
                            <div key={idx} className="product-card">
                                <img src={product.imageUrl} alt={product.name} />
                                <div className="product-info">
                                    <h4>{product.name}</h4>
                                    <p>${product.price.toFixed(2)}</p>
                                </div>
                                <button
                                    className="add-to-cart-button"
                                    onClick={(e) => {
                                        e.stopPropagation(); // evita propagación de clics
                                        const btn = e.currentTarget;
                                        btn.classList.add('clicked');
                                        setTimeout(() => btn.classList.remove('clicked'), 500);
                                        // Aquí se agregará lógica del carrito después
                                    }}
                                >
                                    +
                                </button>
                            </div>
                        ))}
                    </div>
                </div>
            )}

            {/* Tiendas */}
            <section className="stores">
                <h2>Tiendas disponibles</h2>
                <div className="stores-list">
                    {stores.map((store) => (
                        <div key={store.id} className="store-card" onClick={() => openStoreView(store)}>
                            <img src={`http://localhost:8080/${store.imageUrl}`} alt={store.name} />
                            <div className="store-info">
                                <h3>{store.name}</h3>
                                <p>{store.category} • {store.city}</p>
                                <p>⭐ {store.rating}</p>
                            </div>
                        </div>
                    ))}
                </div>
            </section>
        </div>
    );
}

export default App;
