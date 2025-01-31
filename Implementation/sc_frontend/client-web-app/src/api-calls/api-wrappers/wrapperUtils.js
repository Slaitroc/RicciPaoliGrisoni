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
 * @param {Map<string, string>} fieldMap - A map where keys are the original property names and values are the labels to be used.
 * @param {Array<Object>} data - An array of objects, each containing a `properties` object with key-value pairs to be formatted.
 * @returns {Array<Object>} - An array of objects where each object contains the original keys mapped to objects with `key`, `label`, and `value` properties.
 */
export const formatLabeledArrayContent = (fieldMap, data) => {
  return data.map((offer) => {
    const formattedObject = {};

    // Iteriamo su fieldMap per mantenere l'ordine
    fieldMap.forEach((label, key) => {
      if (offer.properties.hasOwnProperty(key)) {
        formattedObject[key] = {
          serverValue: key,
          label: label, // `label` viene direttamente da `fieldMap`
          value: offer.properties[key], // Valore corrispondente da `offer`
        };
      }
    });

    return formattedObject; // Ora restituisce un oggetto JS, non una Map
  });
};
