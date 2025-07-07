import React, { useState, useEffect } from "react";
import axios from 'axios';
import './App.css';

function App() {
    const [stores, setStores] = useState([]);
    const [cartOpen, setCartOpen] = useState(false);
    const [selectedStore, setSelectedStore] = useState(null);
    const [storeProducts, setStoreProducts] = useState([]);
    const [cart, setCart] = useState([]);

    const [showAddedMessage, setShowAddedMessage] = useState(false);
    const triggerAddedMessage = () => {
        setShowAddedMessage(true);
        setTimeout(() => setShowAddedMessage(false), 2000);
    };
    const [orderMessage, setOrderMessage] = useState(null);


    const addToCart = (product, storeId) => {
        setCart(prevCart => {
            const index = prevCart.findIndex(item => item.productId === product.id);

            if (index !== -1) {
                // Ya existe, aumentar cantidad
                const updatedCart = [...prevCart];
                updatedCart[index].quantity += 1;
                return updatedCart;
            } else {
                return [...prevCart, {
                    productId: product.id,
                    name: product.name,
                    price: product.price,
                    quantity: 1,
                    storeId: storeId
                }];
            }
        });
    };

    const handleComprar = async () => {
        if (cart.length === 0) {
            alert("Tu carrito está vacío.");
            return;
        }

        const storeId = cart[0].storeId; // asumimos que todos los productos son de la misma tienda
        const totalAmount = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

        const order = {
            customerName: "Cliente Demo", // puedes luego usar un input o login
            storeId: storeId,
            totalAmount: totalAmount,
            orderDate: new Date().toISOString(), // opcional
            items: cart.map(item => ({
                productId: item.productId,
                quantity: item.quantity,
                price: item.price
            }))
        };

        try {
            const response = await axios.post("http://localhost:8081/orders", order);

            setOrderMessage(`Pedido Realizado\nID pedido: ${response.data.id}`);

            setCart([]); // Vaciar carrito

            setTimeout(() => {
                setOrderMessage(null); // Ocultar mensaje
                setCartOpen(false);    // Cerrar carrito después del mensaje
            }, 3000); // 3 segundos visible
        } catch (error) {
            console.error("Error al realizar pedido:", error);
            alert("Hubo un error al procesar el pedido.");
        }
    };

    const openStoreView = (store) => {
        setSelectedStore(store);
        setStoreProducts(store.products || []);
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

            <div className={`cart-overlay ${cartOpen ? 'open' : ''}`}>
                <div className="cart-header">
                    <button className="close-cart-button" onClick={() => setCartOpen(false)}>←</button>
                    <h2>Carrito</h2>
                </div>

                {/* 🛒 Contenido del carrito */}
                <div className="cart-content">
                    {cart.length === 0 ? (
                        <p className="cart-empty">Tu carrito está vacío.</p>
                    ) : (
                        <>
                            {cart.map((item, index) => (
                                <div key={index} className="cart-item">
                                    <p>{item.name} x{item.quantity}</p>
                                    <p>${(item.price * item.quantity).toFixed(2)}</p>
                                </div>
                            ))}
                            <hr className="cart-separator" />
                            <p className="cart-total"><strong>Total:</strong> ${cart.reduce((sum, item) => sum + item.price * item.quantity, 0).toFixed(2)}</p>
                        </>
                    )}
                </div>

                {/* Botón comprar */}
                <button className="buy-button" onClick={handleComprar}>Comprar</button>
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
                                        e.stopPropagation();
                                        const btn = e.currentTarget;
                                        btn.classList.add('clicked');
                                        setTimeout(() => btn.classList.remove('clicked'), 500);

                                        addToCart(product, selectedStore.id);  // Agregar al carrito
                                        triggerAddedMessage(); // Mostrar mensaje emergente
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

            {showAddedMessage && (
                <div className="added-to-cart-message">
                    ¡Agregado al Carrito!
                </div>
            )}

            {orderMessage && (
                <div className="order-message">
                    {orderMessage.split('\n').map((line, idx) => (
                        <p key={idx}>{line}</p>
                    ))}
                </div>
            )}

        </div>
    );
}

export default App;
