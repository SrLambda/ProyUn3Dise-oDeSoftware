import React, { useState, useEffect } from "react";

function App() {
    const [stores, setStores] = useState([]);

    // Datos fijos para recomendaciones
    const recommendations = [
        { id: 1, title: "Pizza", image: "https://via.placeholder.com/120?text=Pizza" },
        { id: 2, title: "Sushi", image: "https://via.placeholder.com/120?text=Sushi" },
        { id: 3, title: "Hamburguesa", image: "https://via.placeholder.com/120?text=Hamburguesa" },
        { id: 4, title: "Ensaladas", image: "https://via.placeholder.com/120?text=Ensaladas" },
        { id: 5, title: "Postres", image: "https://via.placeholder.com/120?text=Postres" },
    ];

    // Simulación carga tiendas
    useEffect(() => {
        const dummyStores = [
            { id: 1, name: "La Pizzería", category: "Pizza", city: "Santiago", rating: 4.5, imageUrl: "https://via.placeholder.com/300x150?text=Pizzer%C3%ADa" },
            { id: 2, name: "Sushi House", category: "Sushi", city: "Santiago", rating: 4.8, imageUrl: "https://via.placeholder.com/300x150?text=Sushi" },
            { id: 3, name: "Burger King", category: "Hamburguesas", city: "Concepción", rating: 4.2, imageUrl: "https://via.placeholder.com/300x150?text=Hamburguesa" },
            { id: 4, name: "Green Salad", category: "Ensaladas", city: "Valparaíso", rating: 4.0, imageUrl: "https://via.placeholder.com/300x150?text=Ensaladas" },
        ];
        setStores(dummyStores);
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

            <section className="stores">
                <h2>Tiendas disponibles</h2>
                <div className="stores-list">
                    {stores.map((store) => (
                        <div key={store.id} className="store-card">
                            <img src={store.imageUrl} alt={store.name} />
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
