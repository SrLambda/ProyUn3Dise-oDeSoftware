// src/components/RatingForm.jsx
import React, { useState } from 'react';
import './RatingForm.css'; // Crearemos este archivo para estilos específicos del formulario

// Asume que este componente recibirá el storeId y el userId del usuario actual
// (el userId podrías gestionarlo con un Context o estado global en tu app real)
function RatingForm({ storeId, userId, onRatingSubmitted }) {
    const [score, setScore] = useState(0); // Para la calificación numérica
    const [comment, setComment] = useState(''); // Para el comentario
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);
        setSuccessMessage(null);

        // Validaciones básicas en frontend (adicionales a las de backend)
        if (score < 1 || score > 5) {
            setError('Por favor, selecciona una calificación entre 1 y 5.');
            setLoading(false);
            return;
        }
        if (!comment.trim()) {
            setError('El comentario no puede estar vacío.');
            setLoading(false);
            return;
        }

        // Preparar el objeto de la calificación según RatingDTO
        const ratingData = {
            storeId: storeId,
            userId: userId, // En una app real, este userId vendría de la sesión del usuario
            score: score,
            comment: comment,
        };

        try {
            // **IMPORTANTE**: Verifica el puerto de tu Ratings Service.
            // Si tu StoreClient en Orders/Ratings apunta a 8080 para el StoresService,
            // el RatingsService podría estar en 8081, o configurado en otro puerto.
            // Revisa el application.properties de tu Ratings Service o su main class.
            const response = await fetch('http://localhost:8080/ratings', { // <--- Ajusta esta URL si tu Ratings Service no está en 8080
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(ratingData),
            });

            if (!response.ok) {
                // Si hay errores de validación del backend (ej. score fuera de rango)
                const errorData = await response.json();
                throw new Error(errorData.message || 'Error al enviar la calificación.');
            }

            const newRating = await response.json();
            setSuccessMessage('¡Calificación enviada con éxito!');
            setScore(0); // Limpiar formulario
            setComment(''); // Limpiar formulario

            // Llama a una función pasada por props para refrescar la lista de calificaciones
            // o para notificar que una nueva calificación ha sido enviada
            if (onRatingSubmitted) {
                onRatingSubmitted(newRating);
            }

        } catch (err) {
            console.error('Error al enviar calificación:', err);
            setError(err.message || 'Error al enviar la calificación. Inténtalo de nuevo.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="rating-form-container">
            <h3>Califica esta tienda</h3>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="score">Puntuación:</label>
                    <select
                        id="score"
                        value={score}
                        onChange={(e) => setScore(parseInt(e.target.value))}
                        required
                        className="score-select"
                    >
                        <option value="0">Selecciona una puntuación</option>
                        <option value="1">1 Estrella</option>
                        <option value="2">2 Estrellas</option>
                        <option value="3">3 Estrellas</option>
                        <option value="4">4 Estrellas</option>
                        <option value="5">5 Estrellas</option>
                    </select>
                </div>
                <div className="form-group">
                    <label htmlFor="comment">Comentario:</label>
                    <textarea
                        id="comment"
                        value={comment}
                        onChange={(e) => setComment(e.target.value)}
                        rows="4"
                        placeholder="Escribe tu comentario aquí..."
                        required
                        className="comment-textarea"
                    ></textarea>
                </div>
                <button type="submit" disabled={loading} className="submit-button">
                    {loading ? 'Enviando...' : 'Enviar Calificación'}
                </button>

                {error && <p className="error-message">{error}</p>}
                {successMessage && <p className="success-message">{successMessage}</p>}
            </form>
        </div>
    );
}

export default RatingForm;