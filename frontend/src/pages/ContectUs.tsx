
export function ContactUs() {
    return (
        <main>
            <section className="contact-section container">
                <h2>Contact Us</h2>
                <form action="submit-contact-form.php" method="post" className="contact-form">
                    <label htmlFor="name">Name:</label>
                    <input type="text" id="name" name="name" required />
                    <label htmlFor="email">Email:</label>
                    <input type="email" id="email" name="email" required />
                    <label htmlFor="message">Message:</label>
                    <textarea id="message" name="message" rows={5} required></textarea>
                    <button type="submit">Send Message</button>
                </form>
                <div className="contact-info">
                    <p><strong>Email:</strong> support@moviesocialsite.com</p>
                    <p><strong>Phone:</strong> +123 456 7890</p>
                    <p><strong>Address:</strong> 123 Movie Street, Film City, CA 90210</p>
                </div>
            </section>
        </main>
    )
}