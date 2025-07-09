import React, { useState, useEffect } from "react";
import axios from 'axios';
import pizzaImage from './assets/pizza.png';
import sushiImage from './assets/sushi.png';
import burguerImage from './assets/burger.png';
import saladImage from './assets/salad.png';
import postresImage from './assets/postres.png';
import './App.css'; // Asegúrate de que este CSS contiene los estilos de los modales

function App() {
    const [stores, setStores] = useState([]);
    const [cartOpen, setCartOpen] = useState(false);
    const [selectedStore, setSelectedStore] = useState(null);
    const [storeProducts, setStoreProducts] = useState([]);
    const [cart, setCart] = useState([]);
    const [showAddedMessage, setShowAddedMessage] = useState(false);
    const [orderMessage, setOrderMessage] = useState(null);

    // --- NUEVOS ESTADOS PARA AUTENTICACIÓN ---
    const [loginModalOpen, setLoginModalOpen] = useState(false);
    const [registerModalOpen, setRegisterModalOpen] = useState(false);
    const [loginMessage, setLoginMessage] = useState('');
    const [registerMessage, setRegisterMessage] = useState('');
    const [loggedInUser, setLoggedInUser] = useState(null); // {id: ..., username: ..., email: ...}

    // URL base de tu microservicio de usuarios (asegúrate que sea correcta)
    const USER_SERVICE_URL = 'http://localhost:8080/api/users'; // Por defecto, users-service en 8080.


    // --- FUNCIONES EXISTENTES ---
    const triggerAddedMessage = () => {
        setShowAddedMessage(true);
        setTimeout(() => setShowAddedMessage(false), 2000);
    };

    const addToCart = (product, storeId) => {
        setCart(prevCart => {
            const index = prevCart.findIndex(item => item.productId === product.id);
            if (index !== -1) {
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
        if (!loggedInUser) {
            setOrderMessage("Debes iniciar sesión para realizar un pedido.");
            setTimeout(() => setOrderMessage(null), 3000);
            return;
        }


        const storeId = cart[0].storeId; // asumimos que todos los productos son de la misma tienda
        const totalAmount = cart.reduce((sum, item) => sum + item.price * item.quantity, 0);

        const order = {
            accountId: loggedInUser.id, // Usar el ID del usuario logueado
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
            // Asegúrate de que el orders-service esté en el puerto 8081
            const response = await axios.post("http://localhost:8081/orders", order);
            setOrderMessage(`Pedido Realizado\nID pedido: ${response.data.id}`);
            setCart([]); // Vaciar carrito
            setTimeout(() => {
                setOrderMessage(null); // Ocultar mensaje
                setCartOpen(false);    // Cerrar carrito después del mensaje
            }, 3000); // 3 segundos visible
        } catch (error) {
            console.error("Error al realizar pedido:", error);
            setOrderMessage("Hubo un error al procesar el pedido.");
            setTimeout(() => setOrderMessage(null), 3000);
        }
    };

    const openStoreView = (store) => {
        setSelectedStore(store);
        // Aquí deberías hacer una llamada al API para obtener los productos de la tienda
        // Asumo que tu endpoint para productos es GET /api/products?storeId=X
        axios.get(`http://localhost:8080/product?storeId=${store.id}`) // Asumiendo que el servicio de tiendas maneja los productos
            .then(response => {
                setStoreProducts(response.data);
            })
            .catch(error => {
                console.error("Error al obtener productos de la tienda:", error);
                setStoreProducts([]); // Vaciar si hay error
            });
    };

    const recommendations = [
        { id: 1, title: "Pizza", image: pizzaImage },
        { id: 2, title: "Sushi", image: sushiImage },
        { id: 3, title: "Hamburguesa", image: burguerImage },
        { id: 4, title: "Ensaladas", image: saladImage },
        { id: 5, title: "Postres", image: postresImage },
    ];

    // Simulación carga tiendas (ajusta la URL si tu servicio de tiendas está en otro puerto)
    useEffect(() => {
        axios.get("http://localhost:8080/store") // Asumiendo que el servicio de tiendas está en 8080
            .then((response) => {
                setStores(response.data);
                console.log("Tiendas obtenidas:", response.data); // DEBUG
            })
            .catch((error) => {
                console.error("Error al obtener tiendas:", error);
            });
    }, []);

    // --- NUEVAS FUNCIONES PARA AUTENTICACIÓN ---

    // Efecto para verificar si el usuario ya está logueado al cargar la página
    useEffect(() => {
        const storedUser = localStorage.getItem('loggedInUser');
        if (storedUser) {
            setLoggedInUser(JSON.parse(storedUser));
        }
    }, []);

    const showMessage = (setter, message, isSuccess) => {
        setter(message);
        // Podrías añadir clases CSS dinámicamente aquí si tuvieras un elemento <p> con ref
        // Para este ejemplo, solo el texto y una pequeña pausa
        setTimeout(() => setter(''), 3000); // Limpiar mensaje después de 3 segundos
    };

    const handleRegisterSubmit = async (event) => {
        event.preventDefault();
        const username = event.target.registerUsername.value;
        const email = event.target.registerEmail.value;
        const password = event.target.registerPassword.value;

        try {
            const response = await axios.post(`${USER_SERVICE_URL}/register`, { username, email, password });
            if (response.status === 201) {
                showMessage(setRegisterMessage, `Usuario "${username}" registrado exitosamente!`, true);
                event.target.reset(); // Limpiar formulario
                setTimeout(() => {
                    setRegisterModalOpen(false);
                    setLoginModalOpen(true); // Abrir modal de login
                    showMessage(setLoginMessage, 'Ahora puedes iniciar sesión.', true);
                }, 1500);
            }
        } catch (error) {
            console.error('Error durante el registro:', error);
            const errorMessage = error.response?.data || 'Hubo un problema de conexión al registrar.';
            showMessage(setRegisterMessage, `Error al registrar: ${errorMessage}`, false);
        }
    };

    const handleLoginSubmit = async (event) => {
        event.preventDefault();
        const usernameOrEmail = event.target.loginUsername.value;
        const password = event.target.loginPassword.value;

        try {
            const response = await axios.post(`${USER_SERVICE_URL}/login`, { usernameOrEmail, password });
            if (response.status === 200) {
                const userData = response.data;
                setLoggedInUser(userData);
                localStorage.setItem('loggedInUser', JSON.stringify(userData)); // Guardar en localStorage
                showMessage(setLoginMessage, `Bienvenido, ${userData.username}!`, true);
                event.target.reset();
                setTimeout(() => {
                    setLoginModalOpen(false);
                }, 1500);
            }
        } catch (error) {
            console.error('Error durante el login:', error);
            const errorMessage = error.response?.data || 'Hubo un problema de conexión al iniciar sesión.';
            showMessage(setLoginMessage, `Error al iniciar sesión: ${errorMessage}`, false);
        }
    };

    const handleLogout = () => {
        setLoggedInUser(null);
        localStorage.removeItem('loggedInUser');
        setCart([]); // Opcional: limpiar carrito al cerrar sesión
        alert('Sesión cerrada.');
    };


    return (
        <div className="app-container">
            <header>
                <img src="/utal_eats_logo.png" alt="Utal Eats Logo" className="logo"/>
                <nav>
                    <ul>
                        <li><a href="#">HOME</a></li>
                        <li><a href="#">MENÚ</a></li>
                        {loggedInUser && <li><a href="#">HISTORIAL</a></li>} {/* Mostrar solo si está logueado */}
                        <li><a href="#">CARRITO</a></li>
                    </ul>
                </nav>
                <div className="auth-buttons">
                    {loggedInUser ? (
                        <>
                            <span style={{ color: 'white', marginRight: '10px' }}>Hola, {loggedInUser.username}</span>
                            <button onClick={handleLogout}>Cerrar Sesión</button>
                        </>
                    ) : (
                        <>
                            <button onClick={() => setLoginModalOpen(true)}>Iniciar Sesión</button>
                            <button onClick={() => setRegisterModalOpen(true)}>Registrarse</button>
                        </>
                    )}
                </div>
            </header>

            <main>
                <h2>Bienvenido a UTAL EATS!</h2>
                <p>Tu plataforma para pedir comida en la UTAL.</p>
                <img src="https://via.placeholder.com/600x300?text=Imagen+Principal+Utal+Eats" alt="Imagen principal" style={{ maxWidth: '100%', height: 'auto', marginTop: '20px' }} />
            </main>

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
                <div className="cart-button" onClick={() => setCartOpen(!cartOpen)}>
                    <img src="/carrito.png" alt="Carrito"/>
                </div>

                {cartOpen && <div className="backdrop" onClick={() => setCartOpen(false)}></div>}

                <div className={`cart-overlay ${cartOpen ? 'open' : ''}`}>
                    <div className="cart-header">
                        <button className="close-cart-button" onClick={() => setCartOpen(false)}>←</button>
                        <h2>Carrito</h2>
                    </div>

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

                    <button className="buy-button" onClick={handleComprar}>Comprar</button>
                </div>
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
                                <img src={product.imageUrl} alt={product.name}/>
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
                                        addToCart(product, selectedStore.id);
                                        triggerAddedMessage();
                                    }}
                                >
                                    +
                                </button>
                            </div>
                        ))}
                    </div>
                </div>
            )}

            <section className="stores">
                <h2>Tiendas disponibles</h2>
                <div className="stores-list">
                    {stores.map((store) => (
                        <div key={store.id} className="store-card" onClick={() => openStoreView(store)}>
                            <img src={`http://localhost:8080/${store.imageUrl}`} alt={store.name}/>
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

            {/* --- Modales de autenticación (renderizado condicional) --- */}
            {loginModalOpen && (
                <div id="loginModal" className="modal">
                    <div className="modal-content">
                        <span className="close-button" onClick={() => setLoginModalOpen(false)}>&times;</span>
                        <h2>Iniciar Sesión</h2>
                        <form onSubmit={handleLoginSubmit}>
                            <input type="text" id="loginUsername" placeholder="Usuario o Correo" required />
                            <input type="password" id="loginPassword" placeholder="Contraseña" required />
                            <button type="submit">Entrar</button>
                            {loginMessage && <p className={`message ${loginMessage.includes('Bienvenido') ? 'success' : 'error'}`}>{loginMessage}</p>}
                        </form>
                    </div>
                </div>
            )}

            {registerModalOpen && (
                <div id="registerModal" className="modal">
                    <div className="modal-content">
                        <span className="close-button" onClick={() => setRegisterModalOpen(false)}>&times;</span>
                        <h2>Registrarse</h2>
                        <form onSubmit={handleRegisterSubmit}>
                            <input type="text" id="registerUsername" placeholder="Usuario" required />
                            <input type="email" id="registerEmail" placeholder="Correo Electrónico" required />
                            <input type="password" id="registerPassword" placeholder="Contraseña" required />
                            <button type="submit">Registrar</button>
                            {registerMessage && <p className={`message ${registerMessage.includes('exitosamente') ? 'success' : 'error'}`}>{registerMessage}</p>}
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
}

export default App;