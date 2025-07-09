import React, { useState, useEffect } from "react";
import axios from 'axios';
import pizzaImage from './assets/pizza.png';
import sushiImage from './assets/sushi.png';
import burguerImage from './assets/burger.png';
import saladImage from './assets/salad.png';
import postresImage from './assets/postres.png';
import './App.css';

function App() {
    const [stores, setStores] = useState([]);
    const [cartOpen, setCartOpen] = useState(false);
    const [selectedStore, setSelectedStore] = useState(null);
    const [storeProducts, setStoreProducts] = useState([]);
    const [cart, setCart] = useState([]);
    const [showStarSelector, setShowStarSelector] = useState(false);
    const [selectedStars, setSelectedStars] = useState("⭐");
    const [commentText, setCommentText] = useState("");
    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState("");
    const [userPanelOpen, setUserPanelOpen] = useState(false);
    const [showLogin, setShowLogin] = useState(false);
    const [showRegister, setShowRegister] = useState(false);
    const [regEmail, setRegEmail] = useState("");
    const [regPass, setRegPass] = useState("");
    const [regNombre, setRegNombre] = useState("");
    const [regDireccion, setRegDireccion] = useState("");
    const [regTelefono, setRegTelefono] = useState("");
    const [loginEmail, setLoginEmail] = useState("");
    const [loginPass, setLoginPass] = useState("");
    const [usuarioActivo, setUsuarioActivo] = useState(null);

    const [showAddedMessage, setShowAddedMessage] = useState(false);
    const triggerAddedMessage = () => {
        setShowAddedMessage(true);
        setTimeout(() => setShowAddedMessage(false), 2000);
    };
    const [orderMessage, setOrderMessage] = useState(null);

    const handleStarSelect = (stars) => {
        setSelectedStars(`${stars}⭐`);
        setShowStarSelector(false);
    };

    useEffect(() => {
        const handleClickOutside = (event) => {
            const target = event.target;
            if (!target.closest(".agregar-star") && !target.closest(".comment-star-button")) {
                setShowStarSelector(false);
            }
        };

        document.addEventListener("click", handleClickOutside);
        return () => document.removeEventListener("click", handleClickOutside);
    }, []);

    const [showComments, setShowComments] = useState(false);
    const [selectedProductForComments, setSelectedProductForComments] = useState(null);

    const closeComments = async () => {
        console.log("🔴 Cerrando panel de comentarios");
        setShowComments(false);
        setSelectedProductForComments(null);

        // 🔁 Recalcular estrellas de productos
        if (selectedStore) {
            const enrichedProducts = await Promise.all((selectedStore.products || []).map(async (product) => {
                try {
                    const response = await axios.get(`http://localhost:8082/ratings/product/${product.id}`);
                    const ratings = response.data;
                    const avg = ratings.length > 0
                        ? (ratings.reduce((sum, r) => sum + r.score, 0) / ratings.length).toFixed(1)
                        : "Sin ratings";
                    return { ...product, averageRating: avg };
                } catch (error) {
                    console.error(`Error al obtener ratings del producto ${product.id}`, error);
                    return { ...product, averageRating: "Error" };
                }
            }));

            setStoreProducts(enrichedProducts);
        }
    };

    const vaciarCarrito = () => {
        setCart([]);
    };

    useEffect(() => {
        console.log("📺 Estado showComments:", showComments);
    }, [showComments]);

    useEffect(() => {
        axios.get("http://localhost:8083/api/users")
            .then(response => {
                console.log("Usuarios cargados:", response.data);
            })
            .catch(error => {
                console.error("Error al obtener usuarios:", error);
            });
    }, []);

    const fetchComments = async (productId) => {
        try {
            const response = await axios.get(`http://localhost:8082/ratings/product/${productId}`);
            setComments(response.data);
        } catch (error) {
            console.error("❌ Error al cargar comentarios:", error);
        }
    };

    const openCommentsForProduct = (product) => {
        console.log("🟢 Abriendo comentarios para", product.name);
        setSelectedProductForComments(product);
        setShowComments(true);
        fetchComments(product.id);  // 🔁 Ahora busca por productoId
    };

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

    const openStoreView = async (store) => {
        setSelectedStore(store);

        const enrichedProducts = await Promise.all((store.products || []).map(async (product) => {
            try {
                const response = await axios.get(`http://localhost:8082/ratings/product/${product.id}`);
                const ratings = response.data;
                const avg = ratings.length > 0
                    ? (ratings.reduce((sum, r) => sum + r.score, 0) / ratings.length).toFixed(1)
                    : "Sin ratings";
                return { ...product, averageRating: avg };
            } catch (error) {
                console.error(`Error al obtener ratings del producto ${product.id}`, error);
                return { ...product, averageRating: "Error" };
            }
        }));

        setStoreProducts(enrichedProducts);
    };

    const recommendations = [
        { id: 1, title: "Pizza", image: pizzaImage },
        { id: 2, title: "Sushi", image: sushiImage },
        { id: 3, title: "Hamburguesa", image: burguerImage },
        { id: 4, title: "Ensaladas", image: saladImage },
        { id: 5, title: "Postres", image: postresImage },
    ];

    // Simulación carga tiendas
    useEffect(() => {
        axios.get("http://localhost:8080/store")
            .then(async (response) => {
                const storesWithRatings = await Promise.all(response.data.map(async (store) => {
                    const ratingsResponse = await axios.get(`http://localhost:8082/ratings/store/${store.id}`);
                    const ratings = ratingsResponse.data;
                    const average = ratings.length > 0
                        ? (ratings.reduce((sum, r) => sum + r.score, 0) / ratings.length).toFixed(1)
                        : "Sin ratings";
                    return { ...store, averageRating: average };
                }));
                setStores(storesWithRatings);
            })
            .catch((error) => {
                console.error("Error al obtener tiendas:", error);
            });
    }, []);

    const handleLogin = async () => {
        try {
            const response = await axios.post("http://localhost:8083/api/users/login", {
                correo: loginEmail,
                contrasena: loginPass
            });
            alert("Sesión iniciada como: " + response.data.correo);

            // Guardar el usuario activo (puedes guardar el perfil también)
            setUsuarioActivo(response.data);  // o response.data.perfil si prefieres

            // Limpiar campos
            setLoginEmail("");
            setLoginPass("");
            setShowLogin(false);
            setUserPanelOpen(false);
        } catch (err) {
            alert("Credenciales incorrectas.");
        }
    };

    const handleRegister = async () => {
        try {
            const cuentaResponse = await axios.post("http://localhost:8083/api/users/register", {
                correo: regEmail,
                contrasena: regPass
            });

            const cuentaId = cuentaResponse.data.id;

            await axios.post(`http://localhost:8083/api/users/${cuentaId}/profile`, {
                nombre: regNombre,
                direccion: regDireccion,
                telefono: regTelefono
            });

            alert("¡Cuenta registrada con éxito!");

            // Limpiar campos
            setRegEmail("");
            setRegPass("");
            setRegNombre("");
            setRegDireccion("");
            setRegTelefono("");
            setShowRegister(false);
            setUserPanelOpen(false);
        } catch (err) {
            alert("Error al registrar: " + (err.response?.data?.correo || "Error desconocido"));
        }
    };

    const handleEnviarRating = async () => {
        if (!selectedProductForComments || !selectedStore) {
            alert("Error: producto o tienda no seleccionados");
            return;
        }

        if (selectedStars === "⭐") {
            alert("Por favor, selecciona una puntuación de estrellas antes de enviar.");
            return;
        }

        const ratingData = {
            storeId: selectedStore.id,
            productId: selectedProductForComments.id,  // ✅ Nuevo: asociar comentario al producto
            userId: 1, // Cambia esto si tienes un sistema de usuarios
            score: parseInt(selectedStars[0]), // ✅ convertir '3⭐' a número
            comment: commentText.trim()
        };

        try {
            const response = await axios.post("http://localhost:8082/ratings", ratingData);
            console.log("✅ Calificación enviada:", response.data);
            alert("¡Gracias por tu calificación!");

            setCommentText("");           // Limpiar comentario
            setSelectedStars("⭐");       // Resetear estrellas
            fetchComments(selectedProductForComments.id); // ✅ Cargar comentarios del producto
        } catch (error) {
            console.error("❌ Error al enviar rating:", error);
            alert("Ocurrió un error al enviar tu calificación.");
        }
    };

    const refreshStoresRatings = async () => {
        try {
            const response = await axios.get("http://localhost:8080/store");
            const storesWithRatings = await Promise.all(response.data.map(async (store) => {
                const ratingsResponse = await axios.get(`http://localhost:8082/ratings/store/${store.id}`);
                const ratings = ratingsResponse.data;
                const average = ratings.length > 0
                    ? (ratings.reduce((sum, r) => sum + r.score, 0) / ratings.length).toFixed(1)
                    : "Sin ratings";
                return { ...store, averageRating: average };
            }));
            setStores(storesWithRatings);
        } catch (error) {
            console.error("Error al actualizar ratings de tiendas:", error);
        }
    };

    return (
        <div className="app-container">
            <header>
                <img src="/utal_eats_logo.png" alt="Utal Eats Logo" className="logo"/>
            </header>

            <section className="recommendations">
                <h2>Recomendaciones para ti</h2>
                <div className="recommendations-list">
                    {recommendations.map((rec) => (
                        <div key={rec.id} className="rec-item">
                            <img src={rec.image} alt={rec.title}/>
                            <p>{rec.title}</p>
                        </div>
                    ))}
                </div>
            </section>

            <div className="contenedor-transparente">
                {/* Botón fijo flotante del usuario */}
                {/* Botón flotante del usuario */}
                <div className="user-button" onClick={() => setUserPanelOpen(!userPanelOpen)}>
                    <div className="user-icon">👤</div>
                    {usuarioActivo?.perfil?.nombre && (
                        <div className="user-name">{usuarioActivo.perfil.nombre}</div>
                    )}
                </div>

                {/* Fondo oscuro para cerrar el panel al hacer clic fuera */}
                {userPanelOpen && <div className="backdrop" onClick={() => {
                    setUserPanelOpen(false);
                    setShowLogin(false);
                    setShowRegister(false);
                }}></div>}

                {/* Botón fijo flotante del carrito */}
                <div className="cart-button" onClick={() => setCartOpen(!cartOpen)}>
                    🛒
                </div>

                {userPanelOpen && (
                    <div className="backdrop" onClick={() => {
                        setUserPanelOpen(false);
                        setShowLogin(false);
                        setShowRegister(false);
                    }}>
                        <div
                            className={`user-overlay ${userPanelOpen ? 'open' : ''}`}
                            onClick={(e) => e.stopPropagation()} // ⛔ Evita que el clic dentro del panel cierre todo
                        >
                            <div className="user-header">
                                {(showLogin || showRegister) ? (
                                    <button
                                        className="close-user-button"
                                        onClick={() => {
                                            setShowLogin(false);
                                            setShowRegister(false);
                                            setLoginEmail("");
                                            setLoginPass("");
                                            setRegEmail("");
                                            setRegPass("");
                                            setRegNombre("");
                                            setRegDireccion("");
                                            setRegTelefono("");
                                        }}
                                    >
                                        ←
                                    </button>
                                ) : (
                                    <button
                                        className="close-user-button"
                                        onClick={() => {
                                            setUserPanelOpen(false);
                                            setLoginEmail("");
                                            setLoginPass("");
                                            setRegEmail("");
                                            setRegPass("");
                                            setRegNombre("");
                                            setRegDireccion("");
                                            setRegTelefono("");
                                        }}
                                    >
                                        ←
                                    </button>
                                )}
                                <h2>
                                    {showLogin ? "Iniciar Sesión" : showRegister ? "Registrarse" : "Usuario"}
                                </h2>
                            </div>

                            <div className="user-content">
                                {!showLogin && !showRegister && (
                                    <>
                                        <button onClick={() => { setShowLogin(true); setShowRegister(false); }}>Iniciar Sesión</button>
                                        <button onClick={() => { setShowRegister(true); setShowLogin(false); }}>Registrarse</button>
                                    </>
                                )}

                                {showLogin && (
                                    <>
                                        <input type="email" placeholder="Correo" value={loginEmail} onChange={e => setLoginEmail(e.target.value)} />
                                        <input type="password" placeholder="Contraseña" value={loginPass} onChange={e => setLoginPass(e.target.value)} />
                                        <button onClick={handleLogin}>Entrar</button>
                                    </>
                                )}

                                {showRegister && (
                                    <>
                                        <input type="email" placeholder="Correo" value={regEmail} onChange={e => setRegEmail(e.target.value)} />
                                        <input type="password" placeholder="Contraseña" value={regPass} onChange={e => setRegPass(e.target.value)} />
                                        <input type="text" placeholder="Nombre" value={regNombre} onChange={e => setRegNombre(e.target.value)} />
                                        <input type="text" placeholder="Dirección" value={regDireccion} onChange={e => setRegDireccion(e.target.value)} />
                                        <input type="number" placeholder="Teléfono" value={regTelefono} onChange={e => setRegTelefono(e.target.value)} />
                                        <button onClick={handleRegister}>Registrar</button>
                                    </>
                                )}
                            </div>
                        </div>
                    </div>
                )}

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
                                <hr className="cart-separator"/>
                                <p className="cart-total">
                                    <strong>Total:</strong> ${cart.reduce((sum, item) => sum + item.price * item.quantity, 0).toFixed(2)}
                                </p>
                            </>
                        )}
                    </div>

                    {/* Botón comprar */}
                    <div className="cart-actions">
                        <button className="buy-button" onClick={handleComprar}>Comprar</button>
                        <button className="empty-cart-button" onClick={vaciarCarrito} title="Vaciar carrito">🗑</button>
                    </div>
                </div>
            </div>

            {selectedStore && (
                <div className="store-overlay">
                    <div className="store-header">
                        <button className="close-store-button" onClick={async () => {
                            setSelectedStore(null);
                            await refreshStoresRatings(); // 🔁 actualizar estrellas al cerrar
                        }}>←</button>
                        <h2>{selectedStore.name}</h2>
                    </div>
                    <div className="product-list">
                        {storeProducts.map((product, idx) => (
                            <div key={idx} className="product-card">
                                <img
                                    src={`http://localhost:8080/${product.imageUrl}`}
                                    alt={product.name}
                                    onError={(e) => {
                                    console.error(`❌ Error al cargar imagen del producto: ${product.name} (${product.imageUrl})`);
                                    console.log(`http://localhost:8080/${product.imageUrl}`);
                                }}
                                    />
                                <div className="product-info">
                                    <h4>{product.name}</h4>
                                    <p>${product.price.toFixed(2)}</p>
                                    <p className="product-rating">⭐ {product.averageRating}</p>
                                </div>
                                <div className="product-actions">
                                    {/* Botón de comentario */}
                                    <button
                                        className="comment-button"
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            const btn = e.currentTarget;
                                            btn.classList.add('clicked');
                                            setTimeout(() => btn.classList.remove('clicked'), 500);
                                            openCommentsForProduct(product);
                                        }}
                                    >
                                        💬
                                    </button>

                                    {/* Botón + para agregar al carrito */}
                                    <button
                                        className="add-to-cart-button"
                                        onClick={(e) => {
                                            e.stopPropagation();
                                            const btn = e.currentTarget;
                                            btn.classList.add('clicked');
                                            setTimeout(() => btn.classList.remove('clicked'), 500);

                                            addToCart(product, selectedStore.id);
                                            triggerAddedMessage();
                                        }}
                                    >
                                        +
                                    </button>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            )}

            <div className={`comments-overlay ${showComments ? 'open' : ''}`}>
                <div className="comments-header">
                    <button className="close-comments-button" onClick={closeComments}>←</button>
                    <h2>Comentarios</h2>
                </div>

                <div className="comments-list">
                    {comments && comments.length === 0 ? (
                        <p>No hay comentarios todavía.</p>
                    ) : (
                        comments.map((comment, index) => (
                            <div key={index} className="comment-item">
                                <p className="comment-user">Usuario {comment.userId}</p>
                                <div className="comment-content">
                                    <span>{comment.comment}</span>
                                    <span>{comment.score}⭐</span>
                                </div>
                            </div>
                        ))
                    )}
                </div>

                <div className="comments-actions">
                    <textarea
                        className="comment-textarea"
                        placeholder="Escribe un comentario..."
                        value={newComment}
                        onChange={(e) => setNewComment(e.target.value)}
                    ></textarea>
                    <button
                        className="comment-send-button"
                        disabled={selectedStars === "⭐"} // Desactivar si no eligió
                        onClick={async () => {
                            try {
                                const payload = {
                                    storeId: selectedStore.id,
                                    userId: 999, // reemplaza con ID real si tienes login
                                    comment: newComment,
                                    score: parseInt(selectedStars[0]),
                                    productId: selectedProductForComments.id,
                                };

                                const response = await axios.post("http://localhost:8082/ratings", payload);

                                console.log("✅ Comentario enviado:", response.data);
                                setNewComment("");              // Limpiar input
                                setSelectedStars("⭐");         // Reiniciar estrellas
                                fetchComments(selectedProductForComments.id); // Actualizar lista
                            } catch (err) {
                                console.error("❌ Error al enviar comentario:", err);
                            }
                        }}
                    >
                        📤
                    </button>
                    <div className="star-container">
                        <button
                            className="comment-star-button"
                            onClick={(e) => {
                                e.stopPropagation();
                                setShowStarSelector((prev) => !prev);
                            }}
                        >
                            {selectedStars}
                        </button>

                        {showStarSelector && (
                            <div className="agregar-star">
                                {[1, 2, 3, 4, 5].map((n) => (
                                    <div key={n} onClick={() => handleStarSelect(n)} className="star-option">
                                        {n}⭐
                                    </div>
                                ))}
                            </div>
                        )}
                    </div>

                </div>
            </div>

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
                                <p>⭐ {store.averageRating}</p>
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

