/**
 * Compiles an ordered map from the given field map and data.
 *
 * @param {Map} fieldMap - A map containing the ordered fields to be included in the resulting map.
 * @param {Array} data - An array of objects, each containing a "properties" inside of which are stored key-value pairs.
 * @returns {Array<Map>} An array of maps, each containing the data Array key-value pairs ordered the fieldMap way removing the properties father object.
 */
export const formatArrayContent = (fieldMap, data) => {
  return data
    .map((offer) => offer.properties)
    .map((offer) => {
      const mapClone = new Map(fieldMap);
      for (const key in offer) {
        if (mapClone.has(key)) {
          mapClone.set(key, offer[key]);
        }
      }
      return mapClone;
    });
};

/**
 * Formats the content of an array of objects by mapping their properties to a new structure.
 *
 * @param {Map<string, string>} fieldMap - Mappa dove le chiavi sono i nomi originali dei campi e i valori sono le etichette.
 * @param {Map<string, string>} fieldTypeMap - Mappa dove le chiavi sono i nomi originali dei campi e i valori sono i tipi dei campi.
 * @param {Array<Object>} data - Un array di oggetti, ciascuno con un `properties` contenente le chiavi da formattare.
 * @returns {Array<Object>} - Un array di oggetti con chiavi originali mappate a oggetti con `key`, `label`, `value` e `type`.
 */
export const formatLabeledArrayContent = (fieldMap, fieldTypeMap, data) => {
  return data.map((offer) => {
    const formattedObject = {};

    // Iteriamo su fieldMap per mantenere l'ordine e aggiungere i campi
    fieldMap.forEach((label, key) => {
      if (offer.properties.hasOwnProperty(key)) {
        formattedObject[key] = {
          serverValue: key,
          label: label, // `label` viene direttamente da `fieldMap`
          value: offer.properties[key], // Valore corrispondente da `offer`
          type: fieldTypeMap.get(key) || "string", // Se il tipo non è definito, di default è "text"
        };
      }
    });

    return formattedObject;
  });
};

export const formatLabeledObjectContent = (fieldMap, fieldTypeMap, offer) => {
  const formattedObject = {};

  // Iteriamo su fieldMap per mantenere l'ordine e aggiungere i campi
  fieldMap.forEach((label, key) => {
    // Verifichiamo se l'oggetto offer contiene la proprietà desiderata
    if (offer.properties && offer.properties.hasOwnProperty(key)) {
      formattedObject[key] = {
        serverValue: key,
        label: label, // il label preso direttamente da fieldMap
        value: offer.properties[key], // il valore corrispondente
        type: fieldTypeMap.get(key) || "string", // se il tipo non è definito, di default è "string"
      };
    }
  });

  return formattedObject;
};
