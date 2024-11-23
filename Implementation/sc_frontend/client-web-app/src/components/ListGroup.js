const ListGroup = () => {
  const items = [
    "Elemento 1",
    "Elemento 2",
    "Elemento 3",
    "Elemento 4",
    "Elemento 5",
  ];

  const data = items.map((item) => {
    return { text: item, id: crypto.randomUUID() };
  });

  message = items.length === 0 ? <li>No element found</li> : null;

  return (
    <>
      <h1>List</h1>
      {message}
      <ul className="list-group">
        {data.map((data) => (
          <li key={data.id} className="list-group-element">
            {data.text}
          </li>
        ))}
      </ul>
    </>
  );

  return data.map((data) => <li className="list-group-item">{data}</li>);
};

export default ListGroup;
